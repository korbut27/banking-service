package com.example.bankingservice.repository;

import com.example.bankingservice.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(
            String username
    );

    @Query(value = """
            INSERT INTO users_phone_numbers (user_id, phone_number)
            VALUES (:userId, :phoneNumber);
             """, nativeQuery = true)
    @Modifying
    void savePhoneNumber(
            @Param("userId") Long userId,
            @Param("phoneNumber") String phoneNumber
    );

    @Query(value = """
            INSERT INTO users_emails (user_id, email)
            VALUES (:userId, :email);
             """, nativeQuery = true)
    @Modifying
    void saveEmail(
            @Param("userId") Long userId,
            @Param("email") String email
    );

    @Query(value = """
            UPDATE users_phone_numbers
            SET phone_number = :phoneNumber
            WHERE phone_number = (
                SELECT phone_number
                FROM users_phone_numbers
                WHERE user_id = :userId
                LIMIT 1
            )
            """, nativeQuery = true)
    @Modifying
    void updatePhoneNumber(
            @Param("userId") Long userId,
            @Param("phoneNumber") String phoneNumber
    );

    @Query(value = """
            UPDATE users_emails
            SET email = :email
            WHERE email = (
                SELECT email
                FROM users_emails
                WHERE user_id = :userId
                LIMIT 1
            )
            """, nativeQuery = true)
    @Modifying
    void updateEmail(
            @Param("userId") Long userId,
            @Param("email") String email
    );

    @Query(value = """
            DELETE FROM users_phone_numbers
            WHERE phone_number = (
                SELECT phone_number
                FROM users_phone_numbers
                WHERE user_id = :userId
                LIMIT 1
            )
            """, nativeQuery = true)
    @Modifying
    void deletePhoneNumber(
            @Param("userId") Long userId
    );

    @Query(value = """
            DELETE FROM users_emails
            WHERE email = (
                SELECT email
                FROM users_emails
                WHERE user_id = :userId
                LIMIT 1
            )
            """, nativeQuery = true)
    @Modifying
    void deleteEmail(
            @Param("userId") Long userId
    );

    @Query(value = """
            SELECT * FROM users u
            WHERE u.birth_date > :birthDate
            """, nativeQuery = true)
    Page<User> findByBirthDate(
            @Param("birthDate") Timestamp birthDate,
            Pageable pageable
    );

    @Query(value = """
            SELECT * FROM users u
            WHERE u.id = (
                SELECT user_id
                FROM users_phone_numbers
                WHERE phone_number = :phoneNumber
            )
            """, nativeQuery = true)
    Page<User> findByPhoneNumber(String phoneNumber, Pageable pageable);

    @Query(value = """
            SELECT * FROM users u
            WHERE u.id = (
                SELECT user_id
                FROM users_emails
                WHERE email = :email
            )
            """, nativeQuery = true)
    Page<User> findByEmail(String email, Pageable pageable);

    @Query(value = """
            SELECT * FROM users WHERE full_name LIKE :fullName%
            """, nativeQuery = true)
    Page<User> findByFullNameLike(String fullName, Pageable pageable);
}
