package com.example.library.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@AllArgsConstructor
public class LibraryAPIException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public LibraryAPIException(String msg, HttpStatus httpStatus, String message) {
        super(msg);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
