package com.org.ChatService.ChatService.controller;

import com.org.ChatService.ChatService.model.DTO.MessageResponseDTO;
import com.org.ChatService.ChatService.model.DTO.SenderReceiverDTO;
import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.model.MessageResponse;
import com.org.ChatService.ChatService.service.ChatMessageService;
import com.org.ChatService.ChatService.service.UserPresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class ChatController {

    public static final Map<String, Boolean> map = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private UserPresenceService userPresenceService;


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        System.out.println("Group message is " + message);
        return message;
    }

    @MessageMapping("/private-message")
    private MessageResponse receivedMessage(@Payload Message message) {
        System.out.println("Message recieved........." + message);
        System.out.println("Thread for Web Socket is ---------------------------" + Thread.currentThread().getName());
        if (chatMessageService.handleReceivedMessage(message)) {
            // User is connected:
            MessageResponseDTO messageResponseDTO = userPresenceService.getFullNameObjByUserName(message);
            System.out.println("Message being send to : " +message.getReceiverUserName() + "/private"  );
            simpMessagingTemplate.convertAndSendToUser(message.getReceiverUserName(), "/private", messageResponseDTO);
            return new MessageResponse("Message Delivered successfully");
        } else {
            // User not connected
            System.out.println("Recipient is currently offline. Message queued for delivery.");
            return new MessageResponse("Recipient is currently offline. Message queued for delivery.");
        }
    }


    @PostMapping("/updateReadFlag")
    public ResponseEntity updateReadFlag(@RequestBody SenderReceiverDTO senderReceiverDTO)
    {
        chatMessageService.updateReadFlag(senderReceiverDTO.getSenderUserName() , senderReceiverDTO.getReceiverUserName());
        return ResponseEntity.ok("Read flag updated Successfully");
    }

    // app/private-message --> Message will be send to this end point
    // user/recieverName/private ----> will be send here for user:

    /*
    GROUP MESSAGING:
     */

}
