package com.example.library.exception;

import com.example.library.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        ErrorDetails message = new ErrorDetails();
        message.setStatusCode(HttpStatus.NOT_FOUND.value());
        message.setTimestamp(new Date());
        message.setMessage(ex.getMessage());
        message.setDescription(request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LibraryAPIException.class)
    public ResponseEntity<ErrorDetails> handleLibraryAPIException(LibraryAPIException ex, WebRequest request) {

        ErrorDetails message = new ErrorDetails();
        message.setStatusCode(HttpStatus.BAD_REQUEST.value());
        message.setTimestamp(new Date());
        message.setMessage(ex.getMessage());
        message.setDescription(request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

        ErrorDetails message = new ErrorDetails();
        message.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        message.setTimestamp(new Date());
        message.setMessage(ex.getMessage());
        message.setDescription(request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex, WebRequest request) {

        ErrorDetails message = new ErrorDetails();
        message.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        message.setTimestamp(new Date());
        message.setMessage(ex.getMessage());
        message.setDescription(request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
