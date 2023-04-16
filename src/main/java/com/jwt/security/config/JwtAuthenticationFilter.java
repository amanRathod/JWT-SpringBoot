package com.jwt.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter provides a convenient way to intercept and modify HTTP requests(security features) before they are processed by the controller
    // RequiredArgsConstructor automatically generates a constructor for a class with final fields as arguments.

    private final JwtService jwtService;


    // doFilterInterl() method checks if the user is authenticated by inspecting the Authentication object in the security context
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String awt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        awt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(awt);
    }
}
