package com.ofg.event.controller;

import com.ofg.event.core.util.response.ResponseUtil;
import com.ofg.event.core.util.results.ApiDataResponse;
import com.ofg.event.core.util.results.ApiResponse;
import com.ofg.event.model.request.EventCreateRequest;
import com.ofg.event.model.request.EventUpdateRequest;
import com.ofg.event.model.response.EventResponse;
import com.ofg.event.service.abstracts.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    private static final String EVENTS_FETCH_SUCCESS = "app.msg.events.fetch.success";
    private static final String EVENT_FETCH_SUCCESS = "app.msg.event.fetch.success";
    private static final String EVENT_CREATE_SUCCESS = "app.msg.event.create.success";
    private static final String EVENT_UPDATE_SUCCESS = "app.msg.event.update.success";
    private static final String EVENT_DELETE_SUCCESS = "app.msg.event.delete.success";

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<EventResponse>>> getAllEvents(
            Pageable pageable,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name
    ) {
        Page<EventResponse> events = eventService.getAllEvents(pageable, categoryId, name);
        return ResponseUtil.createApiDataResponse(events, EVENTS_FETCH_SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiDataResponse<EventResponse>> getEventById(@PathVariable("eventId") long eventId) {
        EventResponse event = eventService.getEventResponseById(eventId);
        return ResponseUtil.createApiDataResponse(event, EVENT_FETCH_SUCCESS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<EventResponse>> addEvent(@Valid @RequestBody EventCreateRequest eventCreateRequest) {
        EventResponse event = eventService.addEvent(eventCreateRequest);
        return ResponseUtil.createApiDataResponse(event, EVENT_CREATE_SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ApiDataResponse<EventResponse>> updateEvent(
            @PathVariable("eventId") long eventId,
            @Valid @RequestBody EventUpdateRequest eventUpdateRequest) {
        EventResponse updatedEvent = eventService.updateEvent(eventId, eventUpdateRequest);
        return ResponseUtil.createApiDataResponse(updatedEvent, EVENT_UPDATE_SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable("eventId") long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseUtil.createApiResponse(EVENT_DELETE_SUCCESS, HttpStatus.NO_CONTENT);
    }
}
