package com.jwt.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    // EnableMethodSecurity is used to enable method-level security annotations such as @PreAuthorize and @PostAuthorize
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                    .requestMatchers("/api/v1/auth/**").permitAll() // white-list
                    .anyRequest().authenticated() // authenticate
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // the server does not store any information about the client between requests
                .and()
                    .authenticationProvider(authenticationProvider) // An authentication provider is responsible for validating a user's credentials and creating an Authentication object if the credentials are valid.
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // This filter is responsible for validating the JWT token sent by the client and setting the Authentication object if the token is valid
                .logout()
                .logoutUrl("/api/v1/auth/logout") // When a user sends a request to this URL, they will be logged out of the system.
                .addLogoutHandler(logoutHandler) // A logout handler is a piece of code that gets executed when a user logs out of the system(cleanup)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()) // It is executed when the logout process is successful
        ;

        return http.build();
    }

}
