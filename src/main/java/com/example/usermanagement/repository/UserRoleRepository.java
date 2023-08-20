package com.example.usermanagement.repository;

import com.example.usermanagement.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
    List<UserRoleEntity> findByUserId(String userId);
}
