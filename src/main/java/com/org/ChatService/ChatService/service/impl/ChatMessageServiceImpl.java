package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.JMS.JmsProducer;
import com.org.ChatService.ChatService.model.DTO.MessageResponseDTO;
import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.repository.MessageRepository;
import com.org.ChatService.ChatService.repository.UserRepository;
import com.org.ChatService.ChatService.service.ChatMessageService;
import com.org.ChatService.ChatService.service.UserPresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private UserPresenceService userPresenceService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private JmsProducer jmsProducer;


    @Override
    public Boolean handleReceivedMessage(Message message) {
        if (userPresenceService.isConnected(message.getReceiverUserName())) {
            // proceed to push in MQ with read flag as true:
            processMessageToMQ(message, message.isRead());
            return true;
        } else {
            // proceed to push in MQ with read flag as false:
            processMessageToMQ(message, message.isRead());
            return false;
        }
    }


    @Override
    public void processMessageToMQ(Message message, Boolean flag) {
        System.out.println("Sending the message to queue");
       message.setRead(flag);
       jmsProducer.sendMessage(message);
    }

    @Override
    public void updateReadFlag(String senderUserName , String receiverUserName)
    {
       messageRepository.updateReadFlag(senderUserName, receiverUserName);

    }
}
