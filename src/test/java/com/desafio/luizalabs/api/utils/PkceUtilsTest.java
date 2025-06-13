package com.desafio.luizalabs.api.utils;


import com.desafio.luizalabs.api.spotify.utils.PkceUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PkceUtilsTest {

    @Test
    @DisplayName("Deve gerar code verifier válido")
    void shouldGenerateValidCodeVerifier() {
        String codeVerifier = PkceUtils.generateCodeVerifier();

        assertThat(codeVerifier)
                .isNotNull()
                .hasSize(43)  // Tamanho padrão do code verifier
                .matches("[A-Za-z0-9_.-~]+");  // Caracteres permitidos
    }

    @Test
    @DisplayName("Deve gerar code challenge válido")
    void shouldGenerateValidCodeChallenge() {
        String codeVerifier = PkceUtils.generateCodeVerifier();
        String codeChallenge = PkceUtils.generateCodeChallenge(codeVerifier);

        assertThat(codeChallenge)
                .isNotNull()
                .hasSize(43)  // Tamanho base64url encoded SHA-256
                .matches("[A-Za-z0-9_-]+");  // Caracteres base64url
    }

    @Test
    @DisplayName("Deve lançar exceção para code verifier nulo")
    void shouldThrowExceptionForNullCodeVerifier() {
        assertThrows(IllegalStateException.class,
                () -> PkceUtils.generateCodeChallenge(null));
    }

    @Test
    @DisplayName("Deve lançar exceção para code verifier vazio")
    void shouldThrowExceptionForEmptyCodeVerifier() {
        assertThrows(IllegalStateException.class,
                () -> PkceUtils.generateCodeChallenge(""));
    }

}
