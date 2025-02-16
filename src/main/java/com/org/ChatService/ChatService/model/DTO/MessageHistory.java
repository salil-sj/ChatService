package com.org.ChatService.ChatService.model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class MessageHistory {
    private String senderUserName;
    private String receiverUserName;

    //Sender firstName:
    private String firstName;
    //Sender Last Name:
    private String lastName;
    private String content;
    private Date timeStamp;


    public MessageHistory(String senderUserName, String receiverUserName, String content, Date timeStamp) {
        this.senderUserName = senderUserName;
        this.receiverUserName = receiverUserName;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public MessageHistory(String senderUserName, String receiverUserName, String firstName, String lastName, String content, Date timeStamp) {
        this.senderUserName = senderUserName;
        this.receiverUserName = receiverUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.content = content;
        this.timeStamp = timeStamp;
    }
}
