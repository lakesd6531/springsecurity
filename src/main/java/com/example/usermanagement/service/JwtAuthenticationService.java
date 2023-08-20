package com.example.usermanagement.service;

import com.example.usermanagement.dto.JwtAuthenticationTokenDTO;
import com.example.usermanagement.model.RoleEntity;
import com.example.usermanagement.model.UserEntity;
import com.example.usermanagement.model.UserRoleEntity;
import com.example.usermanagement.repository.RoleRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.repository.UserRoleRepository;
import com.example.usermanagement.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public JwtAuthenticationTokenDTO authenticateJwt(String token) {
        Jws<Claims> jwt = JwtUtils.getClaim(token);

        // 檢查使用者資料
        Optional<UserEntity> userEntity = userRepository.findById(getUserId(jwt));
        if (!userEntity.isPresent()) {
            throw new BadCredentialsException("查無此用戶資料");
        }

        // 找出該帳號擁有的角色ID
        List<String> roleOidList = userRoleRepository
            .findByUserId(userEntity.get().getId())
            .stream()
            .map(UserRoleEntity::getRoleId)
            .toList();

        List<String> roleNameList = new ArrayList<>();

        for (String roleId : roleOidList) {
            Optional<RoleEntity> roleEntity = roleRepository.findById(roleId);

            if (!roleEntity.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "角色不存在");
            }

            roleNameList.add(roleEntity.get().getName());
        }

        JwtAuthenticationTokenDTO jwtDTO = new JwtAuthenticationTokenDTO();
        jwtDTO.setJwtToken(token);
        jwtDTO.setUserId(userEntity.get().getId());
        jwtDTO.setName(userEntity.get().getName());
        jwtDTO.setRoleNameList(roleNameList);

        return jwtDTO;
    }

    private String getUserId(Jws<Claims> jwt) {
        return (String) jwt.getBody().get("userId");
    }
}
