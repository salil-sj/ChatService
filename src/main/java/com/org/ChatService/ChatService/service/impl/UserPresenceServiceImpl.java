package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.model.DTO.MessageResponseDTO;
import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.model.NameInfo;
import com.org.ChatService.ChatService.repository.UserRepository;
import com.org.ChatService.ChatService.service.UserPresenceService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserPresenceServiceImpl implements UserPresenceService {
    public static final Map<String, Boolean> userConnectedDetails = new ConcurrentHashMap<>();

    public static final Map<String, NameInfo> userNameInfoDetails = new ConcurrentHashMap<>();

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Boolean> getHashMapDetails() {
        return userConnectedDetails;
    }

    @Override
    public Boolean isConnected(String userName) {
        if (userConnectedDetails.containsKey(userName)) {
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
        userConnectedDetails.put(userName, true);
    }


    /*
        Requirement: Need first+last name of sender for populating side bar:
   */
    @Override
    public MessageResponseDTO getFullNameObjByUserName(Message message) {
        if (userNameInfoDetails.containsKey(message.getSenderUserName())) {
            NameInfo nameInfo = userNameInfoDetails.get(message.getSenderUserName());
            MessageResponseDTO messageResponseDTO = new MessageResponseDTO(nameInfo.getFirstName(), nameInfo.getLastName()
                    , message.getSenderUserName(), message.getReceiverUserName(), message.getContent(), message.getCreatedAt(), message.isRead());

            return messageResponseDTO;

            //TODO: Modify this HashMap to something which with LRU algo.
        } else {
            // Query the DB to get the user Details:
            NameInfo nameInfo = userRepository.getFullNameInfoByUSerName(message.getSenderUserName());
            userNameInfoDetails.put(message.getSenderUserName(), nameInfo);

            MessageResponseDTO messageResponseDTO = new MessageResponseDTO(nameInfo.getFirstName(), nameInfo.getLastName()
                    , message.getSenderUserName(), message.getReceiverUserName(), message.getContent(), message.getCreatedAt(), message.isRead());

            return messageResponseDTO;
        }
    }

    @Override
    public NameInfo getUserNameInfo(String userName) {
        return null;
    }
}
