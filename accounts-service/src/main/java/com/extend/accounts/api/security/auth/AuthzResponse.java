package com.extend.accounts.api.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthzResponse {
    boolean isAuthorized;
    private String message;
}
