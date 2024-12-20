package com.ofg.event.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final TokenFilter tokenFilter;
    private final AuthEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfiguration(TokenFilter tokenFilter, AuthEntryPoint authEntryPoint) {
        this.tokenFilter = tokenFilter;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authentication) ->
                        authentication
                                .requestMatchers(HttpMethod.GET, "/favorites").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/favorites/").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, "/favorites/{eventId}").hasAuthority("ROLE_USER")

                                .requestMatchers(HttpMethod.GET, "/reviews/{eventId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/reviews").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/reviews/{reviewId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, "/reviews/{reviewId}").hasAuthority("ROLE_USER")

                                .requestMatchers(HttpMethod.GET, "/registrations/future").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET, "/registrations/past").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/registrations").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.DELETE, "/registrations/{eventId}").hasAuthority("ROLE_USER")

                                .requestMatchers(HttpMethod.GET, "/events").permitAll()
                                .requestMatchers(HttpMethod.GET, "/events/{eventId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/events").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/events/{eventId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/events/{eventId}").hasAuthority("ROLE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/categories").permitAll()
                                .requestMatchers(HttpMethod.GET, "/categories/{categoryId}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/categories/name/{name}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/categories").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/categories/{categoryId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/categories/{categoryId}").hasAuthority("ROLE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/roles").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/roles").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/roles/{roleId}").hasAuthority("ROLE_ADMIN")

                                .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/{userId}").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users/{userId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/users/activate/{token}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/users/update-password/{userId}").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.PUT, "/users/reset-password").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/users/reset-password/verify/{token}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/users/{userId}").hasAuthority("ROLE_USER")

                                .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(authEntryPoint))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.disable())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(corsConfig -> corsConfig.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setMaxAge(3600L);
                    return config;
                }));
        return http.build();
    }
}
