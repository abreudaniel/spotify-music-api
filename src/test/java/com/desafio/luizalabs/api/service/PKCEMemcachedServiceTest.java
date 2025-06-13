package com.desafio.luizalabs.api.service;


import net.rubyeye.xmemcached.MemcachedClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PKCEMemcachedServiceTest {

    @Mock
    private MemcachedClient memcachedClient;

    private PKCEMemcachedService pkceService;

    @BeforeEach
    void setUp() {
        pkceService = new PKCEMemcachedService(memcachedClient);
    }

    @Test
    @DisplayName("Deve armazenar code verifier com sucesso")
    void shouldStoreCodeVerifier() throws Exception {
        String state = "test-state";
        String codeVerifier = "test-verifier";
        String key = "pkce_verifier_"+state;

        pkceService.storeCodeVerifier(state, codeVerifier);

        verify(memcachedClient).set(eq(key), eq(600), eq(codeVerifier));
    }

    @Test
    @DisplayName("Deve recuperar code verifier com sucesso")
    void shouldRetrieveCodeVerifier() throws Exception {
        String state = "test-state";
        String codeVerifier = "test-verifier";
        String key = "pkce_verifier_"+state;

        when(memcachedClient.get(key)).thenReturn(codeVerifier);

        var result = pkceService.getCodeVerifier(state);

        assertThat(result)
                .isPresent()
                .contains(codeVerifier);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando code verifier não existe")
    void shouldReturnEmptyWhenCodeVerifierNotFound() throws Exception {
        String state = "non-existent-state";
        String key = "pkce_verifier_"+state;

        when(memcachedClient.get(key)).thenReturn(null);

        var result = pkceService.getCodeVerifier(state);

        assertThat(result).isEmpty();
    }
}
