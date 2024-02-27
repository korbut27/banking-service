package com.example.bankingservice.domain.user;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirmation;

    @Column(name = "mobile_number")
    @ElementCollection
    @CollectionTable(name = "users_mobile_numbers")
    @Enumerated(value = EnumType.STRING)
    private Set<String> mobileNumbers;

    @Column(name = "email")
    @ElementCollection
    @CollectionTable(name = "users_emails")
    @Enumerated(value = EnumType.STRING)
    private Set<String> emails;

}