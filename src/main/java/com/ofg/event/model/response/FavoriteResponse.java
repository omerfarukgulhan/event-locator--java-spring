package com.ofg.event.model.response;

import com.ofg.event.model.entity.Favorite;

public record FavoriteResponse(
        Long id,
        UserResponse userResponse,
        EventResponse eventResponse
) {
    public FavoriteResponse(Favorite favorite) {
        this(favorite.getId(), new UserResponse(favorite.getUser()),
                new EventResponse(favorite.getEvent()));
    }
}
