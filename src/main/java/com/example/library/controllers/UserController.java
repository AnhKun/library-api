package com.example.library.controllers;

import com.example.library.dto.UserDto;
import com.example.library.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/users")
    public ResponseEntity<UserDto> findUser() {
        var userDto = userService.findUser();

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


}
