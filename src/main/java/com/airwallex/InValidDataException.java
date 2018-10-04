package com.airwallex;

/**
 * InValidData Exception
 */
public class InValidDataException extends Exception {

    private final String message;

    public InValidDataException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
