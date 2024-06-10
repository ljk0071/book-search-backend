package com.booksearch.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final TestFilter testFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] allowedUrls = {"/api/books, /api/user"};
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(List.of("http://localhost:5173"));
//        config.setAllowedMethods(List.of("*"));
//        config.setAllowCredentials(true);
//        config.setAllowedHeaders(List.of("*"));
//        config.setMaxAge(3600L);
        return http
                .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
//                .cors(cors -> cors.configurationSource(request -> config))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize
//                                .anyRequest().permitAll()
//                                .requestMatchers(allowedUrls).permitAll()
//                        .anyRequest()
//                        .authenticated()
//                )
                .addFilterBefore(testFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
