package com.erich.blog.app.handler;

import com.erich.blog.app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail HandlerNotFoundException(NotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Not Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(ComentarioNotFoundExeption.class)
    public ProblemDetail HandlerComentarioNotFoundException(ComentarioNotFoundExeption e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Comentario Not Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(PublicacionNotFoundException.class)
    public ProblemDetail HandlerPublicacionNotFoundException(PublicacionNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Publicacion Not Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ProblemDetail HandlerPublicacionNotFoundException(CategoriaNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Categoria Not Found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail HandlerNotFoundException(BadRequestException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail HandlerMethodArgumenValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        Map<String, String> mapErros = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String campo = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            mapErros.put(campo, message);
        });
        problemDetail.setTitle("Bad_Request");
        problemDetail.setDetail("");
        problemDetail.setProperty("Hora: ", LocalDate.now());
        problemDetail.setProperty("Campos invalidos", mapErros);
        return problemDetail;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ProblemDetail handlerMaxSizeException(MaxUploadSizeExceededException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.EXPECTATION_FAILED);
        problemDetail.setTitle("File muy largo");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail HandlerNotFoundException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("SERVER_ERROR");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora", LocalDate.now());
        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail HandlerAccessDeniedException(AccessDeniedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("UNAUTHORIZED");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("Hora", LocalDate.now());
        return problemDetail;
    }
}
