package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.model.User;
import com.org.ChatService.ChatService.repository.UserRepository;
import com.org.ChatService.ChatService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class UserServiceImpl implements UserService  , UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user==null)
        {
            throw new UsernameNotFoundException("User name : " + username + " not found!!");
        }

        List<GrantedAuthority> authorities = Collections.emptyList();
        return new org.springframework.security.core.userdetails.User(username , user.getPassword() , authorities);

    }

    // For signing in user
    @Override
    public User saveUser(User user) {
        User savedUser = null;

        try {
            if (checkForDuplicateEntry(user)) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                savedUser = userRepository.save(user);
            } else {
                throw new RuntimeException("Duplicate username or email");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while saving the user with error : " + e.getMessage());
        }
        return savedUser;
    }

    public boolean checkForDuplicateEntry(User user) {
        if (userRepository.findByEmail(user.getEmail()).orElse(null) == null || userRepository.findByUsername(user.getUsername()).orElse(null) == null) {
            return true;
        }
        return false;
    }

    public boolean checkUserExists(String user)
    {
        User extractedUer =  userRepository.findByUsername(user).orElse(null);
        if(extractedUer!=null)
        {
            return true;
        }
        else {
            return false;
        }
    }
}
