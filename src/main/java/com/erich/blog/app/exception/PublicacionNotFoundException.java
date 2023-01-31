package com.erich.blog.app.exception;

public class PublicacionNotFoundException extends RuntimeException {

    public PublicacionNotFoundException(String message) {
        super(message);
    }

    public PublicacionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

