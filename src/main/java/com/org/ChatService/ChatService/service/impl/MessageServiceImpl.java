package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.model.DTO.MessageDetailsRequest;
import com.org.ChatService.ChatService.model.DTO.MessageHistory;
import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.model.RecentMessages;
import com.org.ChatService.ChatService.repository.MessageRepository;
import com.org.ChatService.ChatService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService
{
    @Autowired
    private MessageRepository messageRepository;
    @Override
    public Message saveMessage(Message message) {

       Message savedMessage =  messageRepository.save(message);
       return savedMessage;
    }

    @Override
    public List<RecentMessages> getRecentMessageForUser(String userName)
    {
       List<List<Object>> result = messageRepository.getRecentMessage(userName);

        List<RecentMessages> recentMessages  =  result.stream()
                .map(row -> new RecentMessages( (String) row.get(0) , (String) row.get(1) , (String) row.get(2) ,  (String) row.get(3) , (Byte) row.get(4) == 1 ? true : false, (String)row.get(5) , (Date)row.get(6) , (Integer)row.get(7) , (String)row.get(8)))
                .collect(Collectors.toList());

        return recentMessages;
    }

    @Override
    public List<MessageHistory> getMessageHistory(String userA , String userB)
    {
        List<List<Object>> result =  messageRepository.getMessageHistory(userA , userB);
        List<MessageHistory> messageDetailsRequests = result.stream().map(
                row -> new MessageHistory((String) row.get(0) , (String) row.get(1) , (String) row.get(2) , (String) row.get(3), (String) row.get(4),
                        (Date)row.get(5))).collect(Collectors.toList());

        return messageDetailsRequests;
    }

//       this.senderUserName = senderUserName;
//        this.receiverUserName = receiverUserName;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.content = content;
//        this.timeStamp = timeStamp;
}

