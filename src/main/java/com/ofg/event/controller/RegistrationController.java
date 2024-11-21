package com.ofg.event.controller;

import com.ofg.event.core.util.response.ResponseUtil;
import com.ofg.event.core.util.results.ApiDataResponse;
import com.ofg.event.core.util.results.ApiResponse;
import com.ofg.event.model.request.RegistrationCreateRequest;
import com.ofg.event.model.response.RegistrationResponse;
import com.ofg.event.security.CurrentUser;
import com.ofg.event.service.abstracts.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    private static final String REGISTRATIONS_FUTURE_FETCH_SUCCESS = "app.msg.registration.future.fetch.success";
    private static final String REGISTRATIONS_PAST_FETCH_SUCCESS = "app.msg.registration.past.fetch.success";
    private static final String REGISTRATION_CREATE_SUCCESS = "app.msg.registration.create.success";
    private static final String REGISTRATION_CANCEL_SUCCESS = "app.msg.registration.cancel.success";

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/future")
    public ResponseEntity<ApiDataResponse<Page<RegistrationResponse>>> getFutureRegistrations(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                              Pageable pageable) {
        Page<RegistrationResponse> registrations = registrationService.getFutureRegistrations(currentUser.getId(), pageable);
        return ResponseUtil.createApiDataResponse(registrations, REGISTRATIONS_FUTURE_FETCH_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/past")
    public ResponseEntity<ApiDataResponse<Page<RegistrationResponse>>> getPastRegistrations(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                            Pageable pageable) {
        Page<RegistrationResponse> registrations = registrationService.getPastRegistrations(currentUser.getId(), pageable);
        return ResponseUtil.createApiDataResponse(registrations, REGISTRATIONS_PAST_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<RegistrationResponse>> createRegistration(@AuthenticationPrincipal CurrentUser currentUser,
                                                                                    @RequestBody RegistrationCreateRequest registrationCreateRequest) {
        RegistrationResponse registration = registrationService.registerUserForEvent(currentUser.getId(), registrationCreateRequest);
        return ResponseUtil.createApiDataResponse(registration, REGISTRATION_CREATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse> cancelRegistration(@AuthenticationPrincipal CurrentUser currentUser,
                                                          @PathVariable Long eventId
    ) {
        registrationService.cancelRegistration(currentUser.getId(), eventId);
        return ResponseUtil.createApiResponse(REGISTRATION_CANCEL_SUCCESS, HttpStatus.NO_CONTENT);
    }
}
