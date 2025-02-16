package com.org.ChatService.ChatService.controller;

import com.org.ChatService.ChatService.model.AuthResponse;
import com.org.ChatService.ChatService.model.DTO.*;
import com.org.ChatService.ChatService.model.RecentMessages;
import com.org.ChatService.ChatService.model.User;
import com.org.ChatService.ChatService.service.MessageService;
import com.org.ChatService.ChatService.service.UserService;
import com.org.ChatService.ChatService.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MessageService messageService;

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
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        System.out.println("Login method started");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        String token = jwtUtils.generateToken(user.getUsername());
        AuthResponse response = new AuthResponse(token);
        return ResponseEntity.ok(new LoginResponse(token, user.getUsername()));

    }


    @GetMapping(value = "/userExists/{user}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Boolean> userExists(@PathVariable("user") String user) {
        return ResponseEntity.ok(userService.checkUserExists(user));

    }

    @PostMapping("/getProfileDetails")
    public ResponseEntity<UserProfileDTO> getUserDetails(@RequestBody String userName) {
        return ResponseEntity.ok(userService.getUserProfileDetails(userName));
    }


    @PostMapping("/updateProfile")
    public ResponseEntity<Integer> updateProfile(@RequestBody User user)
    {
       return ResponseEntity.ok(userService.updateProfile(user));
    }



    @PostMapping("/getRecentMessage")
    public ResponseEntity<List<RecentMessages>> getRecentMessages(@RequestBody String userName)
    {
        System.out.println("Username is ----------- " + userName);
        List<RecentMessages> recentMessages = messageService.getRecentMessageForUser(userName);
        return ResponseEntity.ok(recentMessages);
    }


    /*
    Search User based on query string for Search Bar
     */
    @PostMapping("/userResults")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<UserSearchResults>> getUserSearchResults(@RequestBody String queryString)
    {
        return  ResponseEntity.ok(userService.getUsersBySearchQuery(queryString));
    }


    @PostMapping("/messageHistory")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<MessageHistory>> getMessageHistory(@RequestBody MessageDetailsRequest messageDetailsRequest)
    {
        return ResponseEntity.ok(messageService.getMessageHistory(messageDetailsRequest.getUserA(), messageDetailsRequest.getUserB()));
    }


}
