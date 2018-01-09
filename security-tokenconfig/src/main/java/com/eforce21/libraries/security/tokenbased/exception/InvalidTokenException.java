package com.eforce21.libraries.security.tokenbased.exception;

public class InvalidTokenException extends Exception {

    private static final long serialVersionUID = -4696529689436924032L;

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}