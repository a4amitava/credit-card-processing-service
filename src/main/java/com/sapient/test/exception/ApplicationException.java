package com.sapient.test.exception;

public class ApplicationException extends RuntimeException {

    private final ErrorCodes errorCodes;

    public ApplicationException(ErrorCodes errorCodes,
                                Throwable throwable) {
        super(errorCodes.getDescription(), throwable);
        this.errorCodes = errorCodes;
    }

    public ErrorCodes getErrorCode() {
        return errorCodes;
    }
}
