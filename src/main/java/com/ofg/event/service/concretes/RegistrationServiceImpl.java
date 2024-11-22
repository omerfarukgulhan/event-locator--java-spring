package com.ofg.event.service.concretes;

import com.ofg.event.exception.general.DuplicateOperationException;
import com.ofg.event.exception.general.NotFoundException;
import com.ofg.event.model.entity.Event;
import com.ofg.event.model.entity.Registration;
import com.ofg.event.model.entity.User;
import com.ofg.event.model.request.RegistrationCreateRequest;
import com.ofg.event.model.response.RegistrationResponse;
import com.ofg.event.repository.RegistrationRepository;
import com.ofg.event.service.abstracts.EventService;
import com.ofg.event.service.abstracts.RegistrationService;
import com.ofg.event.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository,
                                   UserService userService,
                                   EventService eventService) {
        this.registrationRepository = registrationRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public Page<RegistrationResponse> getFutureRegistrations(long userId, Pageable pageable) {
        return registrationRepository.findByUserIdAndEventDateAfter(userId, LocalDateTime.now(), pageable)
                .map(RegistrationResponse::new);
    }

    @Override
    public Page<RegistrationResponse> getPastRegistrations(long userId, Pageable pageable) {
        return registrationRepository.findByUserIdAndEventDateBefore(userId, LocalDateTime.now(), pageable)
                .map(RegistrationResponse::new);
    }

    @Override
    public RegistrationResponse registerUserForEvent(long userId, RegistrationCreateRequest registrationCreateRequest) {
        User user = userService.getUserEntityById(userId);
        Event event = eventService.getEventEntityById(registrationCreateRequest.eventId());

        Registration registration = new Registration();
        registration.setUser(user);
        registration.setEvent(event);

        try {
            Registration savedRegistration = registrationRepository.save(registration);
            eventService.increaseEventsCurrentRegistrations(registrationCreateRequest.eventId());
            return new RegistrationResponse(savedRegistration);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateOperationException("User", "registration", userId, registrationCreateRequest.eventId());
        }
    }

    @Override
    public void cancelRegistration(long userId, long eventId) {
        Registration registration = registrationRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new NotFoundException(eventId));

        eventService.decreaseEventsCurrentRegistrations(eventId);

        registrationRepository.delete(registration);
    }
}

