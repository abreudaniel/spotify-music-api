/*
package com.desafio.luizalabs.api.service;

import com.desafio.luizalabs.api.spotify.restclient.*;
import com.desafio.luizalabs.api.spotify.utils.PkceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    private final SpotifyAuthClient authClient;
    private final SpotifyUserClient spotifyUserClient;

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Value("${spotify.grant_type}")
    private String grantType;

    @Value("${spotify.grant_type_callback}")
    private String grantTypeCallback;

    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    @Value("${spotify.scope}")
    private String scope;

    public SpotifyService(SpotifyAuthClient authClient, SpotifyUserClient spotifyUserClient) {
        this.authClient = authClient;
        this.spotifyUserClient = spotifyUserClient;
    }

    public SpotifyProfileResponse getProfile() {
        return spotifyUserClient.getProfile();
    }

    public AuthorizeResponse authorize(String codeVerifier, String state) {
        Map<String, String> params = getSpotifyAuthorizeParams(codeVerifier);
        SpotifyAuthorizeRequest spotifyAuthorizeRequest = new SpotifyAuthorizeRequest(
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

        return new AuthorizeResponse(authorizeUrl);
    }

    public SpotifyAuthResponse createToken(String code, String codeVerifier) {
        Map<String, String> params = exchangeCodeForToken(code, codeVerifier);
        SpotifyAuthRequest spotifyAuthRequest = new SpotifyAuthRequest(
                params.get("grant_type"),
                params.get("client_id"),
                code,
                params.get("redirect_uri"),
                codeVerifier
        );

        return authClient.createToken(spotifyAuthRequest);
    }

    public Map<String, String> getSpotifyParams() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", grantType);
        params.put("scope", scope);

        return params;

    }

    private Map<String, String> getSpotifyAuthorizeParams(String codeVerifier) {

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("response_type", "code");
        params.put("redirect_uri", redirectUri);
        params.put("scope", scope);
        params.put("code_challenge_method", "S256");
        params.put("code_challenge", PkceUtils.generateCodeChallenge(codeVerifier));

        return params;

    }

    public String buildAuthorizationUri() {
        return UriComponentsBuilder
                .fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .toUriString();
    }

    private Map<String, String> exchangeCodeForToken(String code, String codeVerifier) {

        Map<String, String> form = new HashMap<>();
        form.put("grant_type", grantTypeCallback);
        form.put("code", code);
        form.put("redirect_uri", redirectUri);
        form.put("client_id", clientId);
        form.put("code_verifier", codeVerifier);

        return form;
    }

}
*/
