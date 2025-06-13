package com.desafio.luizalabs.api.spotify.utils;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.Base64;

public class PkceUtils {
    public static String generateCodeVerifier() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }

    public static String generateCodeChallenge(String codeVerifier) {
        try {
            if(codeVerifier == null || codeVerifier.isEmpty()) {
                throw new IllegalStateException("Code verifier não encontrado ou expirado");
            }
            byte[] bytes = codeVerifier.getBytes("UTF-8");
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(bytes);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar code challenge", e);
        }
    }

}
