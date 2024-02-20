package com.example.starter.model.entity;

import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy= SEQUENCE, generator = "USERS_SEQUENCE_GENERATOR")
    @SequenceGenerator(name="USERS_SEQUENCE_GENERATOR",
            sequenceName="USERS_ID_SEQ",
            allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    private String password;

    public UserEntity(){}

    public UserEntity(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = getSha256(password);
    }

    private String getSha256(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        }
        catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public Long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = getSha256(password);
    }
}