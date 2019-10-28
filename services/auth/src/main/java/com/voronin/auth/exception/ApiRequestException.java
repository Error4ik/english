package com.voronin.auth.exception;

/**
 * ApiRequestException class.
 *
 * @author Alexey Voronin.
 * @since 26.10.2019.
 */
public class ApiRequestException extends RuntimeException {

    /**
     * Constructor.
     *
     * @param message error message.
     */
    public ApiRequestException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message error message.
     * @param cause   throwable.
     */
    public ApiRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
