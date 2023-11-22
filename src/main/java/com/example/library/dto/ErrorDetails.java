package com.example.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDetails {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
