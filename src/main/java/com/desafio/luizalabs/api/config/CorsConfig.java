package com.desafio.luizalabs.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;
    @Value("${app.cors.allowed-methods}")
    private String[] allowedMethods;
    @Value("${app.cors.allowed-headers}")
    private String[] allowedHeaders;
    @Value("${app.cors.max-age}")
    private long maxAge;
    @Value("${app.cors.allow-credentials}")
    private boolean allowCredentials;

    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/api/**")
                .allowedOrigins(this.allowedOrigins)
                .allowedMethods(this.allowedMethods)
                .allowedHeaders(this.allowedHeaders)
                .allowCredentials(this.allowCredentials)
                .maxAge(this.maxAge);
    }


}
