package com.desafio.luizalabs.api.rest;


import com.desafio.luizalabs.api.spotify.restclient.SpotifyUserClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/profile")
public class SpotifyProfileController {

    private final SpotifyUserClient spotifyUserClient;

    public SpotifyProfileController(SpotifyUserClient spotifyUserClient) {
        this.spotifyUserClient = spotifyUserClient;
    }


    @GetMapping("/me")
    ResponseEntity<String> getProfile() {

/*        var accessToken=Objects.requireNonNull(spotifyAuthController.login().getBody()).getAccess_token();

        var response = spotifyUserClient.getProfile("Bearer "+ accessToken);*/

        return ResponseEntity.ok().build();
    }

}
