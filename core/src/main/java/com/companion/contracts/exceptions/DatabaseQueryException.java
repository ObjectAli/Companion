package com.companion.contracts.exceptions;

public class DatabaseQueryException extends RuntimeException {

    public DatabaseQueryException(String message) {
        super(message);
    }

}
