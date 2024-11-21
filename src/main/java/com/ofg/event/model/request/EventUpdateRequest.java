package com.ofg.event.model.request;

import com.ofg.event.model.entity.Event;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record EventUpdateRequest(
        @NotBlank(message = "{app.constraint.event-name.not-blank}")
        @Size(max = 255, message = "{app.constraint.event-name.size}")
        String name,
        @NotBlank(message = "{app.constraint.event-description.not-blank}")
        @Size(max = 5000, message = "{app.constraint.event-description.size}")
        String description,
        @NotBlank(message = "{app.constraint.event-location.not-blank}")
        @Size(max = 255, message = "{app.constraint.event-location.size}")
        String location,
        @DecimalMin(value = "-90.0", message = "{app.constraint.event-latitude.decimal-min}")
        @DecimalMax(value = "90.0", message = "{app.constraint.event-latitude.decimal-max}")
        double latitude,
        @DecimalMin(value = "-180.0", message = "{app.constraint.event-longitude.decimal-min}")
        @DecimalMax(value = "180.0", message = "{app.constraint.event-longitude.decimal-max}")
        double longitude,
        @NotBlank(message = "{app.constraint.event-image.not-blank}")
        String eventImage,
        @NotNull(message = "{app.constraint.event-datetime.not-null}")
        @Future(message = "{app.constraint.event-datetime.future}")
        LocalDateTime dateTime,
        @Min(value = 1, message = "{app.constraint.event-max-capacity.min}")
        int maxCapacity,
        @NotNull(message = "{app.constraint.event-category-id.not-null}")
        Long categoryId
) {
    public Event toEvent() {
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setLocation(location);
        event.setLatitude(latitude);
        event.setLongitude(longitude);
        event.setEventImage(eventImage);
        event.setDateTime(dateTime);
        event.setMaxCapacity(maxCapacity);
        return event;
    }
}
