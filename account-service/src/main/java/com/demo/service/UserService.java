package com.demo.service;

import com.demo.model.User;
import com.demo.payload.response.UserProfile;
import com.demo.repo.UserRepository;
import com.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserProfile getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return UserProfile.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(r -> r.getName().name()).toList())
                .build();
    }

    public UserProfile getCurrentUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new NotFoundException("Unauthenticated");
        }
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NotFoundException("User not found"));
        return UserProfile.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(r -> r.getName().name()).toList())
                .build();
    }

    public User addUser(com.demo.dto.User user) {
        return userRepository.save(
                User.builder().email(user.getEmail()).username(user.getUsername()).password(user.getPassword()).build()
        );
    }

}
