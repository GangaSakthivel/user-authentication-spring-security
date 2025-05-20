package com.security_jwt.service;

import com.security_jwt.dto.UserRequestDTO;
import com.security_jwt.model.User;
import com.security_jwt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User dtoToEntity(UserRequestDTO userRequestDTO){

        User user = new User();
        user.setUserName(userRequestDTO.getUserName());
        user.set

    }

}
