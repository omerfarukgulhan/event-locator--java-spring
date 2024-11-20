package com.ofg.event.service.concretes;

import com.ofg.event.exception.authentication.AuthenticationException;
import com.ofg.event.exception.authentication.UserInactiveException;
import com.ofg.event.model.entity.User;
import com.ofg.event.model.request.SignInCredentials;
import com.ofg.event.model.request.UserCreateRequest;
import com.ofg.event.model.response.AuthResponse;
import com.ofg.event.model.response.UserResponse;
import com.ofg.event.security.token.Token;
import com.ofg.event.security.token.TokenService;
import com.ofg.event.service.abstracts.AuthService;
import com.ofg.event.service.abstracts.UserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public AuthServiceImpl(UserService userService,
                           PasswordEncoder passwordEncoder,
                           TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public AuthResponse login(SignInCredentials signInCredentials) {
        User user = userService.getUserByEmail(signInCredentials.email());
        validateUser(user, signInCredentials.password());
        Token token = tokenService.createToken(user, signInCredentials);
        return new AuthResponse(new UserResponse(user), token);
    }

    @Override
    public UserResponse register(UserCreateRequest userCreateRequest) {
        return userService.addUser(userCreateRequest);
    }

    private void validateUser(User user, String rawPassword) {
        if (!user.isActive()) {
            throw new UserInactiveException();
        }
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new AuthenticationException();
        }
    }
}