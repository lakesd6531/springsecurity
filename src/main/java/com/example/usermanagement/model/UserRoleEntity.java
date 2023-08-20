package com.example.usermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_role")
public class UserRoleEntity {

    /**
     * 識別碼
     */
    @Id
    @Column(name = "user_role_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userRoleId;

    /**
     * 使用者識別碼
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 角色識別碼
     */
    @Column(name = "role_id")
    private String roleId;

}
