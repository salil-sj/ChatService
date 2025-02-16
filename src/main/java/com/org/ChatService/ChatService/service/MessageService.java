package com.org.ChatService.ChatService.service;

import com.org.ChatService.ChatService.model.DTO.MessageHistory;
import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.model.RecentMessages;

import java.util.List;

public interface MessageService
{
    public Message saveMessage(Message message);

    public List<RecentMessages> getRecentMessageForUser(String userName);

    public List<MessageHistory> getMessageHistory(String userA , String userB);
}
