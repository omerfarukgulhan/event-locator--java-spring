package com.ofg.event.service.concretes;

import com.ofg.event.exception.general.DuplicateOperationException;
import com.ofg.event.exception.general.NotFoundException;
import com.ofg.event.model.entity.Event;
import com.ofg.event.model.entity.Favorite;
import com.ofg.event.model.entity.User;
import com.ofg.event.model.request.FavoriteCreateRequest;
import com.ofg.event.model.response.FavoriteResponse;
import com.ofg.event.repository.FavoriteRepository;
import com.ofg.event.service.abstracts.EventService;
import com.ofg.event.service.abstracts.FavoriteService;
import com.ofg.event.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, EventService eventService, UserService userService) {
        this.favoriteRepository = favoriteRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Override
    public Page<FavoriteResponse> getFavorites(long userId, Pageable pageable) {
        return favoriteRepository.findByUserId(userId, pageable)
                .map(FavoriteResponse::new);
    }

    @Override
    public FavoriteResponse addFavorite(long userId, FavoriteCreateRequest favoriteCreateRequest) {
        User user = userService.getUserEntityById(userId);
        Event event = eventService.getEventEntityById(favoriteCreateRequest.eventId());

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setEvent(event);

        try {
            Favorite saved = favoriteRepository.save(favorite);
            return new FavoriteResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateOperationException("User", "favorite", userId, favoriteCreateRequest.eventId());
        }
    }

    @Override
    public void removeFavorite(long userId, long eventId) {
        Favorite favorite = favoriteRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new NotFoundException(eventId));

        favoriteRepository.delete(favorite);
    }
}
