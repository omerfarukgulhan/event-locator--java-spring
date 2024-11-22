package com.ofg.event.model.request;

import jakarta.validation.constraints.NotNull;

public record FavoriteCreateRequest (
        @NotNull(message = "Event id cannot be null")
        long eventId
) {

}
