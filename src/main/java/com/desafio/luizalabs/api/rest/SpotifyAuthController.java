package com.desafio.luizalabs.api.rest;

import com.desafio.luizalabs.api.service.PKCEMemcachedService;
import com.desafio.luizalabs.api.service.SpotifyService;
import com.desafio.luizalabs.api.spotify.restclient.*;
import com.desafio.luizalabs.api.spotify.utils.PkceUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${app.cors.allowed-origins}")
public class SpotifyAuthController {

    private final PKCEMemcachedService pkceMemcachedService;
    private final SpotifyService spotifyService;


    public SpotifyAuthController(PKCEMemcachedService pkceMemcachedService,
                                 SpotifyService spotifyService
    ) {
        this.pkceMemcachedService = pkceMemcachedService;
        this.spotifyService = spotifyService;
    }

    @GetMapping("/login")
    public ResponseEntity<AuthorizeResponse> initiateAuth() {
        // Gerar code_verifier
        String codeVerifier = PkceUtils.generateCodeVerifier();
        String state = generateState();

        // Salva na sessão(Redis)
        //pkceSessionService.saveCodeVerifier(state, codeVerifier);
        // Armazenar o code_verifier (Memcached)
        pkceMemcachedService.storeCodeVerifier(state, codeVerifier);
        System.out.println("login codeVerifier Memcached:: ");
        AuthorizeResponse authorizeResponse = spotifyService.authorize(codeVerifier, state);

        return ResponseEntity.ok(authorizeResponse);
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(
            @RequestParam String code,
            @RequestParam String state
    ) {

        // Recupera o code verifier da sessão (Redis)
        //String codeVerifierSession = pkceSessionService.getCodeVerifier(state);

        // Recuperar o code_verifier do Memcached
        String codeVerifier = pkceMemcachedService.getCodeVerifier(state)
                .orElseThrow(() -> new IllegalStateException("Estado inválido ou expirado"));

        System.out.println("Callback codeVerifier Memcached:: " + codeVerifier + " state::  " + state);

        SpotifyAuthResponse spotifyAuthResponse = spotifyService.createToken(code, codeVerifier);

        return ResponseEntity.ok(spotifyAuthResponse);
    }

    private String generateState() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}

