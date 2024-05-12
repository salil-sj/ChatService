package com.org.ChatService.ChatService.JMS;

import com.org.ChatService.ChatService.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsProducer
{
    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${active-mq.queue}")
    private String queue;


    public void sendMessage(Message message)
    {
        try
        {
            jmsTemplate.convertAndSend(queue, message);
            System.out.println("Message pushed successfully to queue - {} "+ queue);
        }
        catch (Exception e)
        {
            System.out.println("Error while sending message to queue " + e.getMessage());
        }
    }
}
