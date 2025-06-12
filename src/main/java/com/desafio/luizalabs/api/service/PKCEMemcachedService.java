package com.desafio.luizalabs.api.service;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Service
public class PKCEMemcachedService {
    private static final String CODE_VERIFIER_PREFIX = "pkce_verifier_";
    private static final int EXPIRATION_TIME = 600; // 10 minutos

    private final MemcachedClient memcachedClient;

    public PKCEMemcachedService(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void storeCodeVerifier(String state, String codeVerifier) {
        try {
            memcachedClient.set(CODE_VERIFIER_PREFIX + state, EXPIRATION_TIME, codeVerifier);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new RuntimeException("Erro ao armazenar code verifier no Memcached", e);
        }
    }

    public Optional<String> getCodeVerifier(String state) {
        try {
            String key = CODE_VERIFIER_PREFIX + state;
            String codeVerifier = memcachedClient.get(key);
            if (codeVerifier != null) {
                memcachedClient.delete(key);
            }
            return Optional.ofNullable(codeVerifier);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new RuntimeException("Erro ao recuperar code verifier do Memcached", e);
        }
    }
}
