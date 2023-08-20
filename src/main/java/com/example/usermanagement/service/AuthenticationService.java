package com.example.usermanagement.service;

import com.example.usermanagement.dto.LoginDTO;
import com.example.usermanagement.model.UserEntity;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final static Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;

    public String login(LoginDTO loginDTO) {
        UserEntity userEntity = userRepository.findByAccount(loginDTO.getAccount());

        // 檢查 user 是否存在
        if (userEntity == null) {
            log.warn("該用戶 {} 尚未註冊", loginDTO.getAccount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 比較密碼
        if (userEntity.getPassword().equals(loginDTO.getPassword())) {
            return generateToken(userEntity);
        } else {
            log.warn("用戶 {} 的密碼不正確",loginDTO.getAccount());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private String generateToken(UserEntity userEntity) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userEntity.getId());
        payload.put("name", userEntity.getName());

        return JwtUtils.generateToken(payload);
    }
}
