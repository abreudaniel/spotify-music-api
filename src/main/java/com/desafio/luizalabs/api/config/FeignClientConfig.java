package com.desafio.luizalabs.api.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfig {
/*    @Bean
    public RequestInterceptor bearerTokenInterceptor() {
        return requestTemplate -> {
            // Adiciona automaticamente o token em todas as requisições
            requestTemplate.header(
                    HttpHeaders.AUTHORIZATION,
                    "Bearer " + SecurityContextHolder.getContext().getAuthentication()
            );
        };
    }*/

}
