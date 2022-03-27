package com.sapient.test.exception;

public enum ErrorCodes {
    DB_QUERY_TIME_OUT("Data Base query timed out"),
    DB_PERSISTENCE_ERROR("Data Base error while adding card"),
    UNKNOWN_DATA_BASE_ERROR("Unknown Data Base Error");
    private final String description;

    ErrorCodes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
