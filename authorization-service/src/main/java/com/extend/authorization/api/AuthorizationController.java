package com.extend.authorization.api;

import com.extend.authorization.model.AuthzRequest;
import com.extend.authorization.model.AuthzResponse;
import com.extend.authorization.service.AuthorizationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@RequestMapping("v1")
public class AuthorizationController {
    final AuthorizationService authorizationService;
    final JwtHelper jwtHelper;

    public AuthorizationController(AuthorizationService authorizationService, JwtHelper jwtHelper) {
        this.authorizationService = authorizationService;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("authorize")
    public AuthzResponse authorize(@RequestBody AuthzRequest authzRequest) {
        String token = authzRequest.getToken();
        String uri = authzRequest.getRequestedUri();
        String method = authzRequest.getRequestedMethod();

        if (!jwtHelper.isTokenValid(token)) {
            return new AuthzResponse(false, "Authorization is not valid");
        }
        Claims claims = jwtHelper.extractAllClaims(token);
        if (claims == null) {
            return new AuthzResponse(false, "Authorization failed");
        }
        String userId = claims.getSubject();
        boolean authorized = authorizationService.isUserAuthorized(userId, uri, method);
        String message = "Access %s for %s on %s";
        return new AuthzResponse(authorized, authorized ? String.format(message, "Granted", method, uri) :
                String.format(message, "Denied", method, uri));
    }
}
