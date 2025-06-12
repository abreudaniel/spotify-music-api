package com.desafio.luizalabs.api.spotify.restclient;

import feign.form.FormProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyAuthorizeRequest {

    @FormProperty("client_id")
    private String clientId;

    @FormProperty("response_type")
    private String responseType;

    @FormProperty("redirect_uri")
    private String redirectUri;

    @FormProperty("scope")
    private String scope;

    @FormProperty("code_challenge_method")
    private String codeChallengeMethod;

    @FormProperty("code_challenge")
    private String codeChallenge;

    public SpotifyAuthorizeRequest() {
    }

/*    public SpotifyAuthorizeRequest(String clientId, String responseType, String scope) {
        this.clientId = clientId;
        this.responseType = responseType;
        this.scope = scope;
    }*/

    public SpotifyAuthorizeRequest(String clientId, String responseType, String redirectUri, String scope, String codeChallengeMethod, String codeChallenge) {
        this.clientId = clientId;
        this.responseType = responseType;
        this.redirectUri = redirectUri;
        this.scope = scope;
        this.codeChallengeMethod = codeChallengeMethod;
        this.codeChallenge = codeChallenge;
    }
}
