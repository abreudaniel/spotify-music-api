package com.desafio.luizalabs.api.spotify.restclient;


import com.desafio.luizalabs.api.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "SpotifyUserClient",
        url = "https://api.spotify.com"/*,
        configuration = FeignClientConfig.class*/
)
public interface SpotifyUserClient {

    @GetMapping(value = "/v1/me")
    SpotifyProfileResponse getProfile();

}
