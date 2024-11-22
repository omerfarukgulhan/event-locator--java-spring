package com.ofg.event.controller;

import com.ofg.event.core.util.response.ResponseUtil;
import com.ofg.event.core.util.results.ApiDataResponse;
import com.ofg.event.core.util.results.ApiResponse;
import com.ofg.event.model.request.FavoriteCreateRequest;
import com.ofg.event.model.response.FavoriteResponse;
import com.ofg.event.security.CurrentUser;
import com.ofg.event.service.abstracts.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    private static final String FAVORITES_FETCH_SUCCESS = "app.msg.favorites.fetch.success";
    private static final String FAVORITE_ADD_SUCCESS = "app.msg.favorite.add.success";
    private static final String FAVORITE_REMOVE_SUCCESS = "app.msg.favorite.remove.success";

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<FavoriteResponse>>> getAllFavorites(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                   Pageable pageable) {
        Page<FavoriteResponse> favorites = favoriteService.getFavorites(currentUser.getId(), pageable);
        return ResponseUtil.createApiDataResponse(favorites, FAVORITES_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<FavoriteResponse>> addFavorite(@AuthenticationPrincipal CurrentUser currentUser,
                                                                         @Valid @RequestBody FavoriteCreateRequest favoriteCreateRequest) {
        FavoriteResponse favorite = favoriteService.addFavorite(currentUser.getId(), favoriteCreateRequest);
        return ResponseUtil.createApiDataResponse(favorite, FAVORITE_ADD_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse> removeFavorite(@AuthenticationPrincipal CurrentUser currentUser,
                                                      @PathVariable Long eventId) {
        favoriteService.removeFavorite(currentUser.getId(), eventId);
        return ResponseUtil.createApiResponse(FAVORITE_REMOVE_SUCCESS, HttpStatus.NO_CONTENT);
    }
}
