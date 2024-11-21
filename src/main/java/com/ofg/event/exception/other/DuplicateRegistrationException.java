package com.ofg.event.exception.other;

import com.ofg.event.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class DuplicateRegistrationException extends RuntimeException {
    public DuplicateRegistrationException(long userId, long eventId) {
        super(Messages.getMessageForLocale(
                "app.msg.duplicate.registration",
                LocaleContextHolder.getLocale(),
                userId,
                eventId
        ));
    }
}
