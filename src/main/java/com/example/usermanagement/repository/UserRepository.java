package com.example.usermanagement.repository;

import com.example.usermanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByAccount(String account);
}
