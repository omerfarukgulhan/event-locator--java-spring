package com.ofg.event.exception.authentication;

import com.ofg.event.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super(Messages.getMessageForLocale("app.msg.auth.invalid.credentials", LocaleContextHolder.getLocale()));
    }
}
