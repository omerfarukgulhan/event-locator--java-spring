package com.ofg.event.service.abstracts;

import com.ofg.event.model.entity.Event;
import com.ofg.event.model.request.EventCreateRequest;
import com.ofg.event.model.request.EventUpdateRequest;
import com.ofg.event.model.response.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    Page<EventResponse> getAllEvents(Pageable pageable, Long categoryId, String name);

    EventResponse getEventResponseById(long eventId);

    Event getEventEntityById(long eventId);

    List<Event> getUpcomingEventsWithinMinutes(int minutes);

    EventResponse addEvent(EventCreateRequest eventCreateRequest);

    EventResponse updateEvent(long eventId, EventUpdateRequest eventUpdateRequest);

    void increaseEventsCurrentRegistrations(long eventId);

    void decreaseEventsCurrentRegistrations(long eventId);

    void deleteEvent(long eventId);
}