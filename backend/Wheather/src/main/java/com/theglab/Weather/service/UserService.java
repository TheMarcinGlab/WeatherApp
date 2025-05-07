package com.theglab.Weather.service;

import com.theglab.Weather.model.User;
import com.theglab.Weather.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User createIfNotExists(String email, String role) {
        return userRepository.findById(email).orElseGet(() -> {
            User u = new User();
            u.setEmail(email);
            u.setRole(role);
            return userRepository.save(u);
        });
    }
    public List<String> findRolesByEmail(String email) {
        return userRepository.findById(email)
                .map(user -> List.of(user.getRole()))
                .orElse(List.of()); // zwraca pustą listę jeśli użytkownik nie istnieje
    }


}
