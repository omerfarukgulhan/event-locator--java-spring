package com.ofg.event.model.response;

import com.ofg.event.model.entity.Registration;

public record RegistrationResponse(
        Long id,
        UserResponse userResponse,
        EventResponse eventResponse
) {
    public RegistrationResponse(Registration registration) {
        this(registration.getId(), new UserResponse(registration.getUser()),
                new EventResponse(registration.getEvent()));
    }
}
