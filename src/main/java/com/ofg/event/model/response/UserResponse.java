package com.ofg.event.model.response;

import com.ofg.event.model.entity.Role;
import com.ofg.event.model.entity.User;

import java.util.Set;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String profileImage,
        Set<Role> roles
) {
    public UserResponse(User user) {
        this(user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName(),
                user.getProfileImage(), user.getRoles());
    }
}