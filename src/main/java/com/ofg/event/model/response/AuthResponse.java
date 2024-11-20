package com.ofg.event.model.response;

import com.ofg.event.security.token.Token;

public record AuthResponse(UserResponse userResponse, Token token) {
}
