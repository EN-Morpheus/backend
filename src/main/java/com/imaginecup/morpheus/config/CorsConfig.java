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

        CorsConfiguration memberConfig = new CorsConfiguration();
        memberConfig.setAllowCredentials(true);
        memberConfig.addAllowedOrigin("http://localhost:3000");
        memberConfig.addAllowedHeader("*");
        memberConfig.setAllowedMethods(Arrays.asList("GET", "POST"));

        source.registerCorsConfiguration("*", config);
        return new CorsFilter(source);
    }
}