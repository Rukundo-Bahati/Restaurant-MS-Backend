package com.restaurant.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Bearer token found, continuing filter chain");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken  = authHeader.substring(7);
        System.out.println("JWT token extracted: " + jwtToken);

        final String userEmail = jwtAuthenticationProvider.getUsername(jwtToken);
        System.out.println("User email from token: " + userEmail);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            System.out.println("Loaded userDetails: " + userDetails.getUsername());
            if(jwtAuthenticationProvider.isTokenValid(jwtToken, userDetails.getUsername())) {
                System.out.println("Token is valid");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("Token is NOT valid");
            }
        } else {
            System.out.println("User email is null or Authentication already set");
        }

        filterChain.doFilter(request, response);
    }

}