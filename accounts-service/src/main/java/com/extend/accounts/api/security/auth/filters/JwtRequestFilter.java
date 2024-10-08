package com.extend.accounts.api.security.auth.filters;

import com.extend.accounts.api.security.auth.AuthzRequest;
import com.extend.accounts.api.security.auth.AuthzResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${api.auth.service.url}")
    private String authServiceUrl;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public JwtRequestFilter(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            if (validateWithAuthzService(jwtToken, request)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken("username", null,
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                objectMapper.writeValue(response.getWriter(), new AuthzResponse(false, "Not authorized"));
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean validateWithAuthzService(String jwtToken, HttpServletRequest request) {
        AuthzRequest authzRequest = new AuthzRequest(jwtToken, request.getRequestURI(), request.getMethod());
        try {
            AuthzResponse authzResponse = restTemplate.postForObject(authServiceUrl, authzRequest, AuthzResponse.class);
            return authzResponse != null && authzResponse.isAuthorized();
        } catch (RestClientException e) {
            //TODO Logging
            e.printStackTrace();
        }
        return false;
    }
}
