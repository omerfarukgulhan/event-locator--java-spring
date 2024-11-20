package com.ofg.event.model.response;

import com.ofg.event.model.entity.User;

public record UsersListResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String profileImage
) {
    public UsersListResponse(User user) {
        this(user.getId(), user.getEmail(),
                user.getFirstName(), user.getLastName(),
                user.getProfileImage());
    }
}
