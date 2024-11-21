package com.ofg.event.service.abstracts;

import com.ofg.event.model.request.RegistrationCreateRequest;
import com.ofg.event.model.response.RegistrationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegistrationService {
    Page<RegistrationResponse> getFutureRegistrations(long userId, Pageable pageable);

    Page<RegistrationResponse> getPastRegistrations(long userId, Pageable pageable);

    RegistrationResponse registerUserForEvent(long userId, RegistrationCreateRequest registrationCreateRequest);

    void cancelRegistration(long userId, long eventId);
}
