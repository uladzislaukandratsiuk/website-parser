package com.website.parser.exception.handler;

import com.website.parser.exception.ReviewNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({ReviewNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse serverExceptionHandler(ReviewNotFoundException ex) {
        return logAndRespond(ex.getMessage(), ex);
    }

    private ApiErrorResponse logAndRespond(final String customMessage, final Exception ex) {
        log.error("Caught Error with message = {}", customMessage, ex);
        return new ApiErrorResponse(customMessage);
    }
}
