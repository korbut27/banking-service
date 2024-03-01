package com.example.bankingservice.domain.account;

import com.example.bankingservice.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "initial_deposit")
    private BigDecimal initialDeposit;

    public Account(User user, BigDecimal initialDeposit) {
        this.user = user;
        this.initialDeposit = initialDeposit;
        this.balance = initialDeposit;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
