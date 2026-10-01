package com.companion.feature.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private Long expiresIn; // опционально - время жизни токена в секундах
    private static final String TYPE = "Bearer";


    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}