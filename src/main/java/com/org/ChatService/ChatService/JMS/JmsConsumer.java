package com.org.ChatService.ChatService.JMS;

import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumer
{

    @Autowired
    private MessageService messageService;

    @JmsListener(destination = "${active-mq.queue}" , containerFactory = "connectionFactory")
    public void consumerChatMessages(Message message)
    {
        System.out.println("Started to consume messages " + message );
        Message savedMessage =  messageService.saveMessage(message);
        if(savedMessage!=null)
        {
            System.out.println("Message saved successfully");
        }


    }
}
