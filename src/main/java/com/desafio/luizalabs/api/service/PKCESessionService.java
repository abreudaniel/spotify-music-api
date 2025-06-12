package com.desafio.luizalabs.api.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class PKCESessionService {
    private final HttpSession session;
    private static final String CODE_VERIFIER_PREFIX = "PKCE_CODE_VERIFIER_";
    private static final String STATE_PREFIX = "PKCE_STATE_";
    private static final int SESSION_TIMEOUT = 600; // 10 minutos em segundos


    public PKCESessionService(HttpSession session) {
        this.session = session;
    }

    /**
     * Salva o code verifier na sessão
     */
    public void saveCodeVerifier(String state, String codeVerifier) {
        session.setAttribute(CODE_VERIFIER_PREFIX + state, codeVerifier);
        session.setAttribute(STATE_PREFIX + state, state);
        session.setMaxInactiveInterval(SESSION_TIMEOUT);
    }

    /**
     * Recupera o code verifier da sessão
     */
    public String getCodeVerifier(String state) {
        String savedState = (String) session.getAttribute(STATE_PREFIX + state);
        if (savedState == null || !savedState.equals(state)) {
            throw new IllegalStateException("Estado inválido ou expirado");
        }

        String codeVerifier = (String) session.getAttribute(CODE_VERIFIER_PREFIX + state);
        if (codeVerifier == null) {
            throw new IllegalStateException("Code verifier não encontrado ou expirado");
        }

        // Remove os atributos após uso
        removeCodeVerifier(state);

        return codeVerifier;
    }

    /**
     * Remove o code verifier da sessão
     */
    private void removeCodeVerifier(String state) {
        session.removeAttribute(CODE_VERIFIER_PREFIX + state);
        session.removeAttribute(STATE_PREFIX + state);
    }

    /**
     * Verifica se existe um code verifier para o state
     */
    public boolean hasCodeVerifier(String state) {
        return session.getAttribute(CODE_VERIFIER_PREFIX + state) != null;
    }

}
