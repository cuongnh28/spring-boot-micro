package com.demo.service;

import com.demo.model.User;
import com.demo.repo.UserRepository;
import com.demo.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UnprocessableEntityException("AAA", "NNN"));
    }

    public User addUser(com.demo.dto.User user) {
        return userRepository.save(
                User.builder().email(user.getEmail()).username(user.getUsername()).password(user.getPassword()).build()
        );
    }

}
