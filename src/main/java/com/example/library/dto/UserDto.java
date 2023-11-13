package com.example.library.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    private List<BookDto> books;
}
