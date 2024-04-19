package com.org.ChatService.ChatService.controller;

import com.org.ChatService.ChatService.model.User;
import com.org.ChatService.ChatService.service.UserService;
import com.org.ChatService.ChatService.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/save")
    private ResponseEntity<String> signup(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            if (savedUser != null) {
                return ResponseEntity.ok("User saved successfully");
            } else {
                return ResponseEntity.badRequest().body("An error occured while saving user");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occured while saving user : " + e.getMessage());
        }
    }


    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody User user) {
        System.out.println("Login method started");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(token);

    }

    @PostMapping("/details")
    private ResponseEntity<String> userDetails()
    {
        return ResponseEntity.ok("User details controller");
    }


}
