package com.extend.accounts.api.auth.filters;

import com.extend.accounts.api.auth.AuthzRequest;
import com.extend.accounts.api.auth.AuthzResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            if (isValidWithAuthzService(jwtToken, request)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Not authorized");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isValidWithAuthzService(String jwtToken, HttpServletRequest request) {
        AuthzRequest authzRequest = new AuthzRequest(jwtToken, request.getRequestURI(), request.getMethod());
        try {
            AuthzResponse authzResponse = restTemplate.postForObject(authServiceUrl, authzRequest, AuthzResponse.class);
            return authzResponse != null && authzResponse.isAuthorized();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
