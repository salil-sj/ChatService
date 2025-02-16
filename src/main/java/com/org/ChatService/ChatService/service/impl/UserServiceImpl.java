package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.model.DTO.UserProfileDTO;
import com.org.ChatService.ChatService.model.DTO.UserSearchResults;
import com.org.ChatService.ChatService.model.User;
import com.org.ChatService.ChatService.repository.UserRepository;
import com.org.ChatService.ChatService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User name : " + username + " not found!!");
        }

        List<GrantedAuthority> authorities = Collections.emptyList();
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);

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

    public boolean checkUserExists(String user) {
        User extractedUer = userRepository.findByUsername(user).orElse(null);
        if (extractedUer != null) {
            return true;
        } else {
            return false;
        }
    }


    public UserProfileDTO getUserProfileDetails(String userName) {
        User user = userRepository.findByUsername(userName).orElse(null);
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setFirst_name(user.getFirst_name());
        userProfileDTO.setLast_name(user.getLast_name());
        userProfileDTO.setUsername(user.getUsername());
        userProfileDTO.setPhone_number(user.getPhone_number());

        return userProfileDTO;
    }

    public Integer updateProfile(User user) {
        Integer i =  userRepository.updateUserProfileByUsername(user.getUsername() , user.getPhone_number() , passwordEncoder.encode(user.getPassword()));
       return i;
    }

    public List<UserSearchResults> getUsersBySearchQuery(String searchQuery)
    {
        List<List<Object>> result  =  userRepository.findUserByQueryString(searchQuery);
        List<UserSearchResults> userSearchResults  =  result.stream().map(row -> new UserSearchResults((String)row.get(0) , (String)row.get(1) , (String)row.get(2) ,
                (String)row.get(3))).collect(Collectors.toList());

        return userSearchResults;
    }

}
