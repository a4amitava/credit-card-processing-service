package com.sapient.test.exception;

import java.util.Set;

/**
 * This exception is thrown when card validation is failed.
 * In case of validation failed, this User friendly error messages will be sent as the response of the api
 */
public class CardValidationException extends RuntimeException {

    private final Set<String> errorMessages;

    public CardValidationException(Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }
}
