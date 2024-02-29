package com.example.bankingservice.domain.user;

import com.example.bankingservice.domain.account.Account;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
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
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirmation;

    @OneToOne(mappedBy = "user")
    private Account account;

    @ElementCollection
    @CollectionTable(name = "users_phone_numbers")
    @Column(name = "phone_number")
    private Set<String> phoneNumbers;

    @ElementCollection
    @Column(name = "email")
    @CollectionTable(name = "users_emails")
    private Set<String> emails;

}