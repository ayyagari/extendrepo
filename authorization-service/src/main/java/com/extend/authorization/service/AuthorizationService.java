package com.extend.authorization.service;

public interface AuthorizationService {
    boolean isUserAuthorized(String userId, String uri, String method);
}
