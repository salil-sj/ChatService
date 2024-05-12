package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.repository.MessageRepository;
import com.org.ChatService.ChatService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
