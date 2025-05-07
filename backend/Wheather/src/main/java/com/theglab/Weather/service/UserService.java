package com.theglab.Weather.service;

import com.theglab.Weather.model.User;
import com.theglab.Weather.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getBySub(String sub) {
        return userRepository.findBySub(sub);
    }

    public User createIfNotExists(String sub, String email, String role) {
        return userRepository.findBySub(sub).orElseGet(() -> {
            User u = new User();
            u.setSub(sub);
            u.setEmail(email);
            u.setRole(role);
            return userRepository.save(u);
        });
    }
}
