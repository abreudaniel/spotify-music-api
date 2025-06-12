package com.desafio.luizalabs.api.service;


import com.desafio.luizalabs.api.spotify.utils.PkceUtils;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
public class SpotifyAuthService {

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

    public Map<String,String> getSpotifyParams(){
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", grantType);
        params.put("scope", scope);

        return params;

    }

    public Map<String,String> getSpotifyAuthorizeParams(String codeVerifier){

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

    public Map<String,String> exchangeCodeForToken(String code,String codeVerifier) {

        Map<String, String> form = new HashMap<>();
        form.put("grant_type", grantTypeCallback);
        form.put("code", code);
        form.put("redirect_uri", redirectUri);
        form.put("client_id", clientId);
        form.put("code_verifier", codeVerifier);

        return form;
    }


/*
    private final WebClient webClient = WebClient.create("https://accounts.spotify.com");

    public String buildAuthorizationUri() {
        String scope = "user-read-private user-read-email user-top-read playlist-read-private playlist-modify-public";
        return UriComponentsBuilder
                .fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("client_id", clientId)
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .toUriString();
    }

    public Map<String, Object> exchangeCodeForToken(String code) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("code", code);
        form.add("redirect_uri", redirectUri);
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        return webClient.post()
                .uri("/api/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }*/
}

