package com.extend.authorization.api;

import io.jsonwebtoken.Claims;

public interface JwtHelper {
    boolean isTokenValid(String token);
    Claims extractAllClaims(String token);
}
