package com.org.ChatService.ChatService.service;

import com.org.ChatService.ChatService.model.DTO.MessageResponseDTO;
import com.org.ChatService.ChatService.model.Message;

public interface ChatMessageService
{
    /*
        Returns true - reciever is connected and message is processed.
        Returns false - receiver is disconnected and message is processed
     */
    public Boolean handleReceivedMessage(Message message);

    public void processMessageToMQ(Message message , Boolean flag);


    public void updateReadFlag(String senderUserName , String receiverUserName);

//    public boolean checkFullNameByUserName(String userName);
//
//    public MessageResponseDTO getFullNameObjByUserName(Message message);
}
