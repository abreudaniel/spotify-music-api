package com.desafio.luizalabs.api.spotify.restclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SpotifyProfileResponse {

    @JsonProperty("display_name")
    private String displayName;

    public SpotifyProfileResponse() {
    }

    public SpotifyProfileResponse(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
