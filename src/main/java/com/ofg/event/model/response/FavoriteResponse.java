package com.ofg.event.model.response;

import com.ofg.event.model.entity.Favorite;

public record FavoriteResponse(
        Long id,
        Long userId,
        EventResponse eventResponse
) {
    public FavoriteResponse(Favorite favorite) {
        this(favorite.getId(), favorite.getUser().getId(),
                new EventResponse(favorite.getEvent()));
    }
}
