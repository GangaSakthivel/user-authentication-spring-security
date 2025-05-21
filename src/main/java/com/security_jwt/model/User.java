package com.security_jwt.model;


import com.security_jwt.enums.SalaryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "salary_type", nullable = false)
    private SalaryType salaryType;

    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "user_address", nullable = false)
    private String address;

    @Column(name = "user_active_or_inactive")
    private boolean status;

    @Column(name = "user_photo", columnDefinition = "BYTEA")
    private byte[] photo;


    @Column(name = "user_document", columnDefinition = "BYTEA")
    private byte[] document;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "notes")
    private String notes;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) //many users can have multiple roles(user, admin, manager.etc)
//    private List<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;

    public User() {
    }

    public User(Long id, String userName, String phoneNumber, String userEmail, SalaryType salaryType, Double salary, String address, boolean status, byte[] photo, byte[] document, String password, String notes, Set<Role> roles) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
