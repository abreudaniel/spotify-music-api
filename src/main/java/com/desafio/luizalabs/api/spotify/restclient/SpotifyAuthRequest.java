package com.desafio.luizalabs.api.spotify.restclient;

import feign.form.FormProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyAuthRequest {

    @FormProperty("grant_type")
    private String grantType;

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("client_secret")
    private String clientSecret;

    @FormProperty("scope")
    private String scope;

    @FormProperty("redirect_uri")
    private String redirectUri;

    @FormProperty("code_verifier")
    private String codeVerifier;

    @FormProperty("code")
    private String code;


    public SpotifyAuthRequest() {
    }

    public SpotifyAuthRequest(String grantType, String clientId, String clientSecret, String scope) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }

    public SpotifyAuthRequest(String grantType, String clientId, String code, String redirectUri,String codeVerifier) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.code = code;
        this.redirectUri = redirectUri;
        this.codeVerifier = codeVerifier;
    }
}
