package com.org.ChatService.ChatService.service;

import com.org.ChatService.ChatService.model.User;

public interface UserService
{
    public User saveUser(User user);
    public boolean checkForDuplicateEntry(User user);
}
