package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.JMS.JmsProducer;
import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.service.ChatMessageService;
import com.org.ChatService.ChatService.service.UserPresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private UserPresenceService userPresenceService;

    @Autowired
    private JmsProducer jmsProducer;


    @Override
    public Boolean handleReceivedMessage(Message message) {
        if (userPresenceService.isConnected(message.getReceiver_user_name())) {
            // proceed to push in MQ with read flag as true:
            processMessageToMQ(message, true);
            return true;
        } else {
            // proceed to push in MQ with read flag as false:
            processMessageToMQ(message, false);
            return false;
        }
    }


    @Override
    public void processMessageToMQ(Message message, Boolean flag) {
        System.out.println("Sending the message to queue");
       message.set_read(flag);
       jmsProducer.sendMessage(message);
    }
}
