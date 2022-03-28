package com.sapient.test.exception;

/**
 * An Application exception will be thrown with the error code when the application encountered a error while processing/saving/retrieving data
 */
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
