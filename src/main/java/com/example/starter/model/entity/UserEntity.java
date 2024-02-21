package com.example.starter.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "USERS_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "USERS_SEQUENCE_GENERATOR",
            sequenceName = "USERS_ID_SEQ",
            allocationSize = 1)
    @Setter
    private Long id;
    @Setter
    private String name;
    @Setter
    private String email;
    private String password;

    private String getSha256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setPassword(String password) {
        this.password = getSha256(password);
    }
}