package com.petproject.exception;

/**
 * @author anton.demus
 * @since 2019-08-05
 */
public class ApplicationRuntimeException extends RuntimeException {

    public ApplicationRuntimeException(Throwable cause) {
        super(cause);
    }

    public ApplicationRuntimeException(String message) {
        super(message);
    }
}
