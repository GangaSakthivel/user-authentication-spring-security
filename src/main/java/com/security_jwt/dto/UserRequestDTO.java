package com.security_jwt.dto;

import com.security_jwt.enums.SalaryType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

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

    public UserRequestDTO() {
    }

    public UserRequestDTO(String userName, String phoneNumber, String userEmail, SalaryType salaryType, Double salary, String address, boolean status, MultipartFile photo, MultipartFile document, String password, String notes, Set<String> roles) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.salaryType = salaryType;
        this.salary = salary;
        this.address = address;
        this.status = status;
        this.photo = photo;
        this.document = document;
        this.password = password;
        this.notes = notes;
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public SalaryType getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public MultipartFile getDocument() {
        return document;
    }

    public void setDocument(MultipartFile document) {
        this.document = document;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
