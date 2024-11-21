package com.ofg.event.exception.other;

import com.ofg.event.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class EventCapacityReachedException extends RuntimeException {
    public EventCapacityReachedException(long eventId) {
        super(Messages.getMessageForLocale("app.msg.event.capacity.reached", LocaleContextHolder.getLocale(), eventId));
    }
}