package com.org.ChatService.ChatService.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public interface UserPresenceService
{
    public Boolean isConnected(String userName);

    public void removeUser(String userName);

    public void addUser(String userName);
}
