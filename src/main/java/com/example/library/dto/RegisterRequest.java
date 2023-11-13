package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;


}
