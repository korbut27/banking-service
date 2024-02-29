package com.example.bankingservice.repository;

import com.example.bankingservice.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = """
            UPDATE accounts
            SET balance = CASE
            WHEN id = :senderId THEN balance - :amount
            WHEN id = :recipientId THEN balance + :amount
            ELSE balance
            END
            WHERE id IN (:senderId, :recipientId);        
            """, nativeQuery = true)
    @Modifying
    void transferFunds(
            @Param("senderId") Long senderId,
            @Param("recipientId") Long recipientId,
            @Param("amount") BigDecimal amount
    );
}
