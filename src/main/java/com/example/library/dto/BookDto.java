package com.example.library.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String category;
    @NotEmpty
    private int stock;
}
