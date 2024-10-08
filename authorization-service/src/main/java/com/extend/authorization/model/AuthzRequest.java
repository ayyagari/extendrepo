package com.extend.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthzRequest {
    private String token;
    private String requestedUri;
    private String requestedMethod;
}
