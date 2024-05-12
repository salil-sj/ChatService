package com.org.ChatService.ChatService.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment primary key
    private Integer id;

    @Column(name = "conversation_id")
    private Integer conversationId;

    @Column(name = "sender_user_name", nullable = false)
    private String senderUserName;

    @Column(name = "receiver_user_name")
    private String receiverUserName;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_read")
    private boolean read;
}

