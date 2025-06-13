package com.desafio.luizalabs.api.service;


import com.desafio.luizalabs.api.spotify.restclient.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SpotifyServiceTest {

    @Mock
    private SpotifyAuthClient authClient;

    private SpotifyService spotifyService;

    @BeforeEach
    void setUp() {
        spotifyService = new SpotifyService(authClient);

        // Configurar valores das propriedades
        ReflectionTestUtils.setField(spotifyService, "clientId", "test-client-id");
        ReflectionTestUtils.setField(spotifyService, "clientSecret", "test-client-secret");
        ReflectionTestUtils.setField(spotifyService, "redirectUri", "http://localhost:8080/callback");
        ReflectionTestUtils.setField(spotifyService, "scope", "user-read-private");
        ReflectionTestUtils.setField(spotifyService, "grantType", "authorization_code");
        ReflectionTestUtils.setField(spotifyService, "grantTypeCallback", "authorization_code");
    }

    @Test
    @DisplayName("Deve criar URL de autorização corretamente")
    void shouldCreateAuthorizationUrl() {
        String codeVerifier = "test-verifier";
        String state = "test-state";

        AuthorizeResponse response = spotifyService.authorize(codeVerifier, state);

        assertThat(response.getAuthorizeUrl())
                .contains("https://accounts.spotify.com/authorize")
                .contains("client_id=test-client-id")
                .contains("response_type=code")
                .contains("redirect_uri=http://localhost:8080/callback")
                .contains("code_challenge_method=S256")
                .contains("state=" + state);
    }

    @Test
    @DisplayName("Deve criar token com sucesso")
    void shouldCreateTokenSuccessfully() {
        String code = "test-code";
        String codeVerifier = "test-verifier";
        SpotifyAuthResponse mockResponse = new SpotifyAuthResponse();
        mockResponse.setAccess_token("test-access-token");

        when(authClient.createToken(any())).thenReturn(mockResponse);

        SpotifyAuthResponse response = spotifyService.createToken(code, codeVerifier);

        assertThat(response).isNotNull();
        assertThat(response.getAccess_token()).isEqualTo("test-access-token");
    }

    @Test
    @DisplayName("Deve retornar parâmetros Spotify corretos")
    void shouldReturnCorrectSpotifyParams() {
        var params = spotifyService.getSpotifyParams();

        assertThat(params)
                .containsEntry("client_id", "test-client-id")
                .containsEntry("client_secret", "test-client-secret")
                .containsEntry("grant_type", "authorization_code")
                .containsEntry("scope", "user-read-private");
    }

}
