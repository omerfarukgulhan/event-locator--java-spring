package com.ofg.event.core.util.response;

import com.ofg.event.core.util.message.Messages;
import com.ofg.event.core.util.results.ApiDataResponse;
import com.ofg.event.core.util.results.ApiResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<ApiDataResponse<T>> createApiDataResponse(T data, String messageKey, HttpStatus status) {
        String message = Messages.getMessageForLocale(messageKey, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ApiDataResponse<>(true, message, data), status);
    }

    public static ResponseEntity<ApiResponse> createApiResponse(String messageKey, HttpStatus status) {
        String message = Messages.getMessageForLocale(messageKey, LocaleContextHolder.getLocale());
        return new ResponseEntity<>(new ApiResponse(true, message), status);
    }
}
