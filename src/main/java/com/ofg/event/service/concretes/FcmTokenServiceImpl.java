package com.ofg.event.service.concretes;

import com.ofg.event.model.entity.FcmToken;
import com.ofg.event.repository.FcmTokenRepository;
import com.ofg.event.service.abstracts.FcmTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FcmTokenServiceImpl implements FcmTokenService {
    private final FcmTokenRepository fcmTokenRepository;

    @Autowired
    public FcmTokenServiceImpl(FcmTokenRepository fcmTokenRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
    }

    @Override
    public FcmToken getFcmTokenByUserId(long userId) {
        return fcmTokenRepository.getByUserId(userId).orElse(null);
    }
}
