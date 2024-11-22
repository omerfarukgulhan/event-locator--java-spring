package com.ofg.event.exception.general;

import com.ofg.event.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class DuplicateOperationException extends RuntimeException {
    public DuplicateOperationException(String entity, String operation, long userId, long eventId) {
        super(Messages.getMessageForLocale(
                "app.msg.duplicate",
                LocaleContextHolder.getLocale(),
                entity,
                userId,
                operation,
                eventId
        ));
    }
}

