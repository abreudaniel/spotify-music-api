package com.desafio.luizalabs.api.rest;

import com.desafio.luizalabs.api.service.PKCEMemcachedService;
import com.desafio.luizalabs.api.service.SpotifyAuthService;
import com.desafio.luizalabs.api.spotify.restclient.*;
import com.desafio.luizalabs.api.spotify.utils.PkceUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${app.cors.allowed-origins}")
public class SpotifyAuthController {

    private final SpotifyAuthClient authClient;
    private final SpotifyAuthService spotifyAuthService;
    //private final SpotifyUserClient spotifyUserClient;
    //private final PKCESessionService pkceSessionService;
    private final PKCEMemcachedService pkceMemcachedService;


    public SpotifyAuthController(SpotifyAuthClient authClient,
                                 SpotifyAuthService spotifyAuthService,
                                 PKCEMemcachedService pkceMemcachedService
    ) {
        this.authClient = authClient;
        this.spotifyAuthService = spotifyAuthService;
        this.pkceMemcachedService = pkceMemcachedService;

    }

    @GetMapping("/login")
    public ResponseEntity<AuthorizeResponse> initiateAuth() {
        // Gerar code_verifier
        String codeVerifier = PkceUtils.generateCodeVerifier();
        String state = generateState();

        // Armazenar o code_verifier (em produção, use Redis ou Memcached)
        pkceMemcachedService.storeCodeVerifier(state, codeVerifier);


        System.out.println("login codeVerifier:: " + codeVerifier + " state:: " + state);

        // Obter parâmetros de autorização
        Map<String, String> params = spotifyAuthService.getSpotifyAuthorizeParams(codeVerifier);

        // Construir a URL de autorização
        var authorizeRequest = new SpotifyAuthorizeRequest(
                params.get("client_id"),
                params.get("response_type"),
                params.get("redirect_uri"),
                params.get("scope"),
                params.get("code_challenge_method"),
                params.get("code_challenge")
        );

        // Construir a URL final
        String authorizeUrl = UriComponentsBuilder
                .fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("client_id", params.get("client_id"))
                .queryParam("response_type", params.get("response_type"))
                .queryParam("redirect_uri", params.get("redirect_uri"))
                .queryParam("scope", params.get("scope"))
                .queryParam("code_challenge_method", params.get("code_challenge_method"))
                .queryParam("code_challenge", params.get("code_challenge"))
                .queryParam("state", state)
                .build()
                .toUriString();

        return ResponseEntity.ok(new AuthorizeResponse(authorizeUrl));
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(
            @RequestParam String code,
            @RequestParam String state
    ) {

        System.out.println("callback state:: " + state);

        // Recuperar o code_verifier do Memcached
        String codeVerifier = pkceMemcachedService.getCodeVerifier(state)
                .orElseThrow(() -> new IllegalStateException("Estado inválido ou expirado"));

        // Trocar o código por um token
        Map<String, String> tokenParams = spotifyAuthService
                .exchangeCodeForToken(code, codeVerifier);

        var response = authClient.authToken(new SpotifyAuthRequest(
                tokenParams.get("grant_type"),
                tokenParams.get("client_id"),
                code,
                tokenParams.get("redirect_uri"),
                codeVerifier
        ));

        System.out.println("response CallBack:: " + response.toString());

        return ResponseEntity.ok(response);
    }

    private String generateState() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}

