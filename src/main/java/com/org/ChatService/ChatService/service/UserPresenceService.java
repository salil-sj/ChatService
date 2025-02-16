package com.org.ChatService.ChatService.service;

import com.org.ChatService.ChatService.model.DTO.MessageResponseDTO;
import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.model.NameInfo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public interface UserPresenceService
{

    public Map<String,Boolean> getHashMapDetails();
    public Boolean isConnected(String userName);

    public void removeUser(String userName);

    public void addUser(String userName);

    /*
Requirement: Need first+last name of sender for populating side bar:
   */

    public MessageResponseDTO getFullNameObjByUserName(Message message);

    public NameInfo getUserNameInfo(String userName);
}
