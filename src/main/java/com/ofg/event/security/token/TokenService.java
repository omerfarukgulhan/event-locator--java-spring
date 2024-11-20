package com.ofg.event.security.token;

import com.ofg.event.model.entity.User;
import com.ofg.event.model.request.SignInCredentials;

import java.util.Optional;

public interface TokenService {
    Token createToken(User user, SignInCredentials signInCredentials);

    Optional<User> verifyToken(String authorizationHeader);
}