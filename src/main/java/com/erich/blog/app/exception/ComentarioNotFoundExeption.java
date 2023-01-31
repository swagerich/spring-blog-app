package com.erich.blog.app.exception;

public class ComentarioNotFoundExeption extends RuntimeException{

    public ComentarioNotFoundExeption(String message) {
        super(message);
    }

    public ComentarioNotFoundExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
