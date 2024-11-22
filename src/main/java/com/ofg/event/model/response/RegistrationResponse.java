package com.ofg.event.model.response;

import com.ofg.event.model.entity.Registration;

public record RegistrationResponse(
        Long id,
        Long userId,
        EventResponse eventResponse
) {
    public RegistrationResponse(Registration registration) {
        this(registration.getId(), registration.getUser().getId(),
                new EventResponse(registration.getEvent()));
    }
}
