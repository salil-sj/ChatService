package com.org.ChatService.ChatService.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private String first_name;
    private String last_name;
    private String created_at;
}
