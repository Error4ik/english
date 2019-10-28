package com.voronin.auth.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * ApiException.
 *
 * @author Alexey Voronin.
 * @since 26.10.2019.
 */
public class ApiException {

    /**
     * Error message.
     */
    private final String message;

    /**
     * Error status.
     */
    private final HttpStatus httpStatus;

    /**
     * ZonedDateTime.
     */
    private final ZonedDateTime timestamp;

    /**
     * Constructor.
     *
     * @param message    error message.
     * @param httpStatus httpStatus.
     * @param timestamp  ZonedDateTime.
     */
    public ApiException(final String message, final HttpStatus httpStatus, final ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
