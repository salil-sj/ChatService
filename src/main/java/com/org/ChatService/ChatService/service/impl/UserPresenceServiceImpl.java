package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.service.UserPresenceService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class UserPresenceServiceImpl implements UserPresenceService
{
    public static final Map<String,Boolean> userConnectedDetails = new ConcurrentHashMap<>();

    @Override
    public Boolean isConnected(String userName) {
        if(userConnectedDetails.containsKey(userName))
        {
            return userConnectedDetails.get(userName);
        }
        return false;
    }

    @Override
    public void removeUser(String userName) {
        userConnectedDetails.put(userName, false);
    }

    @Override
    public void addUser(String userName) {
        userConnectedDetails.put(userName,true);
    }
}
