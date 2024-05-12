package com.org.ChatService.ChatService.JMS;

import com.org.ChatService.ChatService.model.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsumer
{
    @JmsListener(destination = "${active-mq.queue}" , containerFactory = "connectionFactory")
    public void consumerChatMessages(Message message)
    {
        System.out.println("Started to consume messages " + message );
        System.out.println("Thread for cosuming is ---------------------------" + Thread.currentThread().getName());

    }
}
