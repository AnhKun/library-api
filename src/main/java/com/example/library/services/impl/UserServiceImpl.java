package com.example.library.services.impl;

import com.example.library.converter.Mapper;
import com.example.library.dto.UserDto;
import com.example.library.entities.UserEntity;
import com.example.library.repositories.IUserRepository;
import com.example.library.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final Mapper mapper;


    @Override
    public UserDto findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ;
        String email = authentication.getName();

        UserEntity userEntity = userRepository.findByEmail(email).get();

        return mapper.toModel(userEntity, UserDto.class);
    }
}
