package com.ofg.event.service.abstracts;

import com.ofg.event.model.entity.FcmToken;

public interface FcmTokenService {
    FcmToken getFcmTokenByUserId(long userId);
}
