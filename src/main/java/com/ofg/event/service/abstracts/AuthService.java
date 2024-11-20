package com.ofg.event.service.abstracts;

import com.ofg.event.model.request.SignInCredentials;
import com.ofg.event.model.request.UserCreateRequest;
import com.ofg.event.model.response.AuthResponse;
import com.ofg.event.model.response.UserResponse;

public interface AuthService {
    AuthResponse login(SignInCredentials signInCredentials);

    UserResponse register(UserCreateRequest userCreateRequest);
}
