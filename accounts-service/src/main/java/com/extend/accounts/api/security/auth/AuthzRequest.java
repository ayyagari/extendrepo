package com.extend.accounts.api.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthzRequest {
    private String token;
    private String requestedUri;
    private String requestedMethod;
}
