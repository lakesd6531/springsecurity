package com.example.usermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

    /**
     * 識別碼
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * 名字
     */
    @Column(name = "name")
    private String name;

    /**
     * 帳號
     */
    @Column(name = "account")
    private String account;

    /**
     * 密碼
     */
    @Column(name = "password")
    private String password;
}
