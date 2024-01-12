package com.imaginecup.morpheus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("*", config);
        return new CorsFilter(source);
    }

    private CorsConfiguration setMemberCors() {
        CorsConfiguration memberConfig = new CorsConfiguration();
        memberConfig.setAllowCredentials(true);
        memberConfig.addAllowedOrigin("http://localhost:3000/**");
        memberConfig.addAllowedHeader("*");
        memberConfig.setAllowedMethods(Arrays.asList("GET", "POST"));

        return memberConfig;
    }

    private CorsConfiguration setCharacterCors() {
        CorsConfiguration characterConfig = new CorsConfiguration();
        characterConfig.setAllowCredentials(true);
        characterConfig.addAllowedOrigin("http://localhost:3000/**");
        characterConfig.addAllowedHeader("Content-Type");
        characterConfig.addAllowedHeader("Authorization");
        characterConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PATCH"));

        return characterConfig;
    }

    private CorsConfiguration setFairyCors() {
        CorsConfiguration fairyConfig = new CorsConfiguration();
        fairyConfig.setAllowCredentials(true);
        fairyConfig.addAllowedOrigin("http://localhost:3000/**");
        fairyConfig.addAllowedHeader("Authorization");
        fairyConfig.setAllowedMethods(Arrays.asList("GET", "POST"));

        return fairyConfig;
    }
}