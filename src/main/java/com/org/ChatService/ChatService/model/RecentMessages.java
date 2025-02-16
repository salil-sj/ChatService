package com.org.ChatService.ChatService.model;

import lombok.Data;

import java.util.Date;

@Data
public class RecentMessages
{

    private String firstName;

    private String lastName;
    private String userName;

    private String recieverUserName;


    private String latestContent;
    private Date createdAt;

    private boolean readFlag;
    private Integer groupId;

    private String groupName;





    public RecentMessages(String userName, boolean readFlag, String latestContent, Date createdAt) {

        this.userName = userName;

        this.latestContent = latestContent;
        this.createdAt = createdAt;
        this.readFlag = readFlag;
    }

    public RecentMessages(String firstName , String lastName,String userName , String recieverUserName,boolean readFlag, String latestContent, Date createdAt , Integer groupId , String groupName) {

        this.userName = userName;
        this.recieverUserName = recieverUserName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.latestContent = latestContent;
        this.createdAt = createdAt;
        this.readFlag = readFlag;
        this.groupId = groupId;
        this.groupName = groupName;

    }


}
