package com.sapient.test.exception;

import java.util.Set;

public class CardValidationException extends RuntimeException {

    private final Set<String> errorMessages;

    public CardValidationException(Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }
}
