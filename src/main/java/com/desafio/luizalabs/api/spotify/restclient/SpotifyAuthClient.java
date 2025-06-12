package com.desafio.luizalabs.api.spotify.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SpotifyAuthClient",
        url = "https://accounts.spotify.com"
)
public interface SpotifyAuthClient {

      @PostMapping(value = "/api/token",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
      SpotifyAuthResponse createToken(@RequestBody SpotifyAuthRequest authRequest);

      @GetMapping(value = "/authorize")
      ResponseEntity<Void> authorize(@RequestParam SpotifyAuthorizeRequest authorizeRequest);
}
