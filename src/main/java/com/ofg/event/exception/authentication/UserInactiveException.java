package com.ofg.event.exception.authentication;

import com.ofg.event.core.util.message.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserInactiveException extends RuntimeException {
    public UserInactiveException() {
        super(Messages.getMessageForLocale("app.msg.user.inactive", LocaleContextHolder.getLocale()));
    }
}