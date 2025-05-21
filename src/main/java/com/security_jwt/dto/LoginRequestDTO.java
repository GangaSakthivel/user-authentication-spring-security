package com.security_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


public class LoginRequestDTO {

    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
