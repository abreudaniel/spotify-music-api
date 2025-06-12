package com.desafio.luizalabs.api.spotify.restclient;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorizeResponse {

    private String authorizeUrl;

    public AuthorizeResponse() {
    }

    public AuthorizeResponse(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }
}
