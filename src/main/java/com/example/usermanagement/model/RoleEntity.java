package com.example.usermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class RoleEntity {

    /**
     * 識別碼
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * 名稱
     */
    @Column(name = "name")
    private String name;
}
