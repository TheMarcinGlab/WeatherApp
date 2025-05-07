package com.theglab.Weather.controller;

import com.theglab.Weather.model.City;
import com.theglab.Weather.model.User;
import com.theglab.Weather.repository.CityRepository;
import com.theglab.Weather.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("api/public")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;


    @PostMapping("createNewUser")
    public ResponseEntity<Void> createUserFromFrontend(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        userRepository.findById(user.getEmail()).orElseGet(() -> {
            user.setRole("ROLE_USER");
            return userRepository.save(user);
        });

        return ResponseEntity.ok().build();
    }


    @GetMapping("getUserRoleByEmail")
    public ResponseEntity<String> getRoleByEmail(@RequestParam String email) {
        return userRepository.findById(email)
                .map(user -> ResponseEntity.ok(user.getRole()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("ROLE_UNKNOWN"));
    }



}
