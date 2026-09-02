package com.example.demo;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 🚀 CRITICAL: Force Spring to explicitly match your exact CloudFront origin
        config.setAllowedOrigins(List.of("https://d3042ckvga29du.cloudfront.net"));
        config.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        config.setAllowedHeaders(List.of("content-type", "Authorization", "*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        
        // Apply this configuration to every incoming route path passing through the container
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

