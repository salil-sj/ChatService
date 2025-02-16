package com.org.ChatService.ChatService.service;

import com.org.ChatService.ChatService.model.DTO.UserProfileDTO;
import com.org.ChatService.ChatService.model.DTO.UserSearchResults;
import com.org.ChatService.ChatService.model.User;

import java.util.List;

public interface UserService
{
    public User saveUser(User user);
    public boolean checkForDuplicateEntry(User user);

    public boolean checkUserExists(String user);

    public UserProfileDTO getUserProfileDetails(String userName);

    public Integer updateProfile(User user);

    public List<UserSearchResults> getUsersBySearchQuery(String searchQuery);

}
