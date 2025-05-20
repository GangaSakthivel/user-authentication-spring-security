package com.security_jwt.model;


import com.security_jwt.enums.RoleName;
import com.security_jwt.enums.SalaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Set<Role> roleName;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;



}
