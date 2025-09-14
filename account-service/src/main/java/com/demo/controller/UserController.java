package com.demo.controller;

import com.demo.payload.response.UserProfile;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public UserProfile getUserBy(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserProfile getMe() {
        return userService.getCurrentUserProfile();
    }

}
