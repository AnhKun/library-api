package com.example.library.services;

import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;

public interface IAuthService {

    String login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);
}
