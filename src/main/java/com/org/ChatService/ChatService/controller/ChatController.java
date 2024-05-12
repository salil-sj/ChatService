package com.org.ChatService.ChatService.controller;

import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.model.MessageResponse;
import com.org.ChatService.ChatService.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ChatController {

    public static final Map<String, Boolean> map = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private ChatMessageService chatMessageService;


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        System.out.println("Message is " + message);
        return message;
    }

    @MessageMapping("/private-message")
    private MessageResponse receivedMessage(@Payload Message message) {
        System.out.println("Thread for Web Socket is ---------------------------" + Thread.currentThread().getName());
        if (chatMessageService.handleReceivedMessage(message)) {
            // User is connected:
            simpMessagingTemplate.convertAndSendToUser(message.getReceiverUserName(), "/private", message);
            return new MessageResponse("Message Delivered successfully");
        } else {
            // User not connected
            return new MessageResponse("Recipient is currently offline. Message queued for delivery.");
        }
    }

    // app/private-message --> Message will be send to this end point
    // user/recieverName/private
}
