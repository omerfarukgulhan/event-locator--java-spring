package com.ofg.event.service.concretes;

import com.ofg.event.exception.general.NotFoundException;
import com.ofg.event.exception.other.EventCapacityReachedException;
import com.ofg.event.model.entity.Category;
import com.ofg.event.model.entity.Event;
import com.ofg.event.model.request.EventCreateRequest;
import com.ofg.event.model.request.EventUpdateRequest;
import com.ofg.event.model.response.EventResponse;
import com.ofg.event.repository.EventRepository;
import com.ofg.event.service.abstracts.CategoryService;
import com.ofg.event.service.abstracts.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryService categoryService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            CategoryService categoryService) {
        this.eventRepository = eventRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Page<EventResponse> getAllEvents(Pageable pageable, Long categoryId, String name) {
        if (categoryId != null) {
            Category category = categoryService.getCategoryEntityById(categoryId);
            if (name != null) {
                return eventRepository.findByCategoryAndNameContaining(category, name, pageable)
                        .map(EventResponse::new);
            } else {
                return eventRepository.findByCategory(category, pageable)
                        .map(EventResponse::new);
            }
        } else if (name != null) {
            return eventRepository.findByNameContainingIgnoreCase(name, pageable)
                    .map(EventResponse::new);
        }

        return eventRepository.findAll(pageable)
                .map(EventResponse::new);
    }

    @Override
    public EventResponse getEventResponseById(long eventId) {
        return eventRepository.findById(eventId)
                .map(EventResponse::new)
                .orElseThrow(() -> new NotFoundException(eventId));
    }

    @Override
    public Event getEventEntityById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId));
    }

    @Override
    public EventResponse addEvent(EventCreateRequest eventCreateRequest) {
        Event event = eventCreateRequest.toEvent();
        event.setCategory(categoryService.getCategoryEntityById(eventCreateRequest.categoryId()));
        Event savedEvent = eventRepository.save(event);
        return new EventResponse(savedEvent);
    }

    @Override
    public EventResponse updateEvent(long eventId, EventUpdateRequest eventUpdateRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId));

        event.setName(eventUpdateRequest.name());
        event.setDescription(eventUpdateRequest.description());
        event.setLocation(eventUpdateRequest.location());
        event.setLatitude(eventUpdateRequest.latitude());
        event.setLongitude(eventUpdateRequest.longitude());
        event.setEventImage(eventUpdateRequest.eventImage());
        event.setDateTime(eventUpdateRequest.dateTime());
        event.setMaxCapacity(eventUpdateRequest.maxCapacity());
        event.setCategory(categoryService.getCategoryEntityById(eventUpdateRequest.categoryId()));

        Event updatedEvent = eventRepository.save(event);
        return new EventResponse(updatedEvent);
    }

    @Override
    public void increaseEventsCurrentRegistrations(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId));

        if (event.getCurrentRegistrations() >= event.getMaxCapacity()) {
            throw new EventCapacityReachedException(eventId);
        }

        event.setCurrentRegistrations(event.getCurrentRegistrations() + 1);
        eventRepository.save(event);
    }

    @Override
    public void decreaseEventsCurrentRegistrations(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId));
        event.setCurrentRegistrations(event.getCurrentRegistrations() - 1);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId));
        eventRepository.delete(event);
    }
}
