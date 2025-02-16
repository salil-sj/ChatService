package com.org.ChatService.ChatService.model.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class MessageResponseDTO {
    private String senderFirstName;

    private String senderLastName;
    private String senderUserName;
    private String receiverUserName;
    private String content;
    private Date createdAt;
    private boolean read;

    public MessageResponseDTO(String senderFirstName, String senderLastName, String senderUserName, String receiverUserName, String content, Date createdAt, boolean read) {
        this.senderFirstName = senderFirstName;
        this.senderLastName = senderLastName;
        this.senderUserName = senderUserName;
        this.receiverUserName = receiverUserName;
        this.content = content;
        this.createdAt = createdAt;
        this.read = read;
    }
}
