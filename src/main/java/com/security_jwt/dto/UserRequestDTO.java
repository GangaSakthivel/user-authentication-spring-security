package com.security_jwt.dto;

import com.security_jwt.enums.SalaryType;
import com.security_jwt.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank
    private String userName;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @NotBlank
    private String userEmail;

    private SalaryType salaryType;

    private Double salary;

    private String address;

    private boolean status;

    private MultipartFile photo;

    private MultipartFile document;

    @NotBlank
    private String password;

    private String notes;

    private Set<String> roles;
}
