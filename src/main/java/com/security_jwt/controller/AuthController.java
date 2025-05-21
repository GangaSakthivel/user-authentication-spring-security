package com.security_jwt.controller;


import com.security_jwt.config.JWTUtils;
import com.security_jwt.dto.BaseResponseDTO;
import com.security_jwt.dto.LoginRequestDTO;
import com.security_jwt.dto.UserRequestDTO;
import com.security_jwt.model.User;
import com.security_jwt.repository.UserRepository;
import com.security_jwt.service.UserService;
import com.security_jwt.utils.ResponseMessages;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {



    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;



    public AuthController(UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository, JWTUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<BaseResponseDTO<String>> register(@Valid @ModelAttribute UserRequestDTO requestDTO) {

        Optional<User> existingUser = userRepository.findByPhoneNumber(requestDTO.getPhoneNumber());
        if (existingUser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new BaseResponseDTO<>(0, "Phone number already registered.", null));
        }

        try {
            User user = userService.registerUser(requestDTO);
            userRepository.save(user);

            return ResponseEntity.ok(new BaseResponseDTO<>(1, ResponseMessages.USER_CREATED, "User registered successfully."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponseDTO<>(0, ResponseMessages.ERROR, null));
        }
    }


    public String loginUser(LoginRequestDTO requestDTO) {
        try {
            // Create authentication token using phoneNumber and password
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(requestDTO.getPhoneNumber(), requestDTO.getPassword());

            // Authenticate using AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(token);

            // If no exception, authentication succeeded
            // You can get the authenticated user details like this:
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate JWT token based on authenticated user details
            String jwtToken = jwtUtils.generateToken(userDetails.getUsername());

            return jwtToken;

        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Invalid phone number or password");
        }
    }


}
