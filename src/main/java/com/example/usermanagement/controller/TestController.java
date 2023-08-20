package com.example.usermanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("hello world");
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/test/admin")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok().body("hi admin");
    }

    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/test/user")
    public ResponseEntity<String> testUser() {
        return ResponseEntity.ok().body("hi user");
    }
}
