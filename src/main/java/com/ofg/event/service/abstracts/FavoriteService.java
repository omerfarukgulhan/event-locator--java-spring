package com.ofg.event.service.abstracts;

import com.ofg.event.model.request.FavoriteCreateRequest;
import com.ofg.event.model.response.FavoriteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {
    Page<FavoriteResponse> getFavorites(long userId, Pageable pageable);

    FavoriteResponse addFavorite(long userId, FavoriteCreateRequest favoriteCreateRequest);

    void removeFavorite(long userId, long eventId);
}
