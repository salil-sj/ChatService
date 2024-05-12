package com.org.ChatService.ChatService.model;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer conversation_id;
    private String sender_user_name;
    private String receiver_user_name;
    private Integer group_id;
    private String content;
    private Date created_at;
    private boolean is_read;
}

