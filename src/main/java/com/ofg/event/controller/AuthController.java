package com.ofg.event.controller;

import com.ofg.event.core.util.response.ResponseUtil;
import com.ofg.event.core.util.results.ApiDataResponse;
import com.ofg.event.model.request.SignInCredentials;
import com.ofg.event.model.request.UserCreateRequest;
import com.ofg.event.model.response.AuthResponse;
import com.ofg.event.model.response.UserResponse;
import com.ofg.event.service.abstracts.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    private static final String LOGIN_SUCCESS = "app.msg.login.success";
    private static final String REGISTER_SUCCESS = "app.msg.register.success";

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<AuthResponse>> login(@Valid @RequestBody SignInCredentials signInCredentials) {
        AuthResponse authResponse = authService.login(signInCredentials);
        return ResponseUtil.createApiDataResponse(authResponse, LOGIN_SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiDataResponse<UserResponse>> register(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        UserResponse userResponse = authService.register(userCreateRequest);
        return ResponseUtil.createApiDataResponse(userResponse, REGISTER_SUCCESS, HttpStatus.CREATED);
    }
}
