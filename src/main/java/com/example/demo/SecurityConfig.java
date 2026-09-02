package com.example.demo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // 🚀 CRITICAL IMPORT
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://d3042ckvga29du.cloudfront.net"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of(
        	    "Content-Type", 
        	    "content-type", 
        	    "Authorization", 
        	    "authorization", 
        	    "X-Requested-With", 
        	    "x-requested-with"
        	));

        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        http
            .cors(cors -> cors.configurationSource(source))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow ALL browser OPTIONS preflight requests anywhere
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                
                // 🚀 FIXED: Wrapped patterns in AntPathRequestMatcher to safely allow multi-segment wildcards
                .requestMatchers(new AntPathRequestMatcher("/prod/api/orchestrator/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**/api/orchestrator/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                
                .anyRequest().permitAll()
            );

        return http.build();
    }
    
}

