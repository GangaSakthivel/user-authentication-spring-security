package com.security_jwt.service;

import com.security_jwt.config.JWTUtils;
import com.security_jwt.dto.UserRequestDTO;
import com.security_jwt.enums.RoleName;
import com.security_jwt.model.Role;
import com.security_jwt.model.User;
import com.security_jwt.repository.RoleRepository;
import com.security_jwt.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public UserService(RoleRepository roleRepository, UserRepository userRepository, JWTUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    private User convertToEntity(UserRequestDTO dto) {
        User user = new User();

        user.setUserName(dto.getUserName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUserEmail(dto.getUserEmail());
        user.setSalary(dto.getSalary());
        user.setSalaryType(dto.getSalaryType());
        user.setStatus(dto.isStatus());
        user.setAddress(dto.getAddress());
        user.setNotes(dto.getNotes());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Convert photo MultipartFile to byte[] if present
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            try {
                user.setPhoto(dto.getPhoto().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read photo file", e);
            }
        }

        // Convert document MultipartFile to byte[] if present
        if (dto.getDocument() != null && !dto.getDocument().isEmpty()) {
            try {
                user.setDocument(dto.getDocument().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read document file", e);
            }
        }

        // Convert role strings to Role entities
        Set<Role> roles = dto.getRoles().stream()
                .map(roleStr -> {
                    RoleName roleName;
                    try {
                        roleName = RoleName.valueOf(roleStr.toUpperCase()); // convert string to enum
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid role: " + roleStr);
                    }

                    return roleRepository.findByRoleName((roleName))
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleStr));
                })
                .collect(Collectors.toSet());

        user.setRoles(roles);

        return user;
    }

    public User registerUser(UserRequestDTO requestDTO) {
        if (userRepository.findByPhoneNumber(requestDTO.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("User with phone number " + requestDTO.getPhoneNumber() + " already exists");
        }

        User user = convertToEntity(requestDTO);
        return userRepository.save(user);
    }
}
