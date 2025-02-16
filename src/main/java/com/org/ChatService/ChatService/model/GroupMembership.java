package com.org.ChatService.ChatService.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "groupmembership")
public class GroupMembership
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="group_id")
    private Integer groupId;
    @Column(name = "username")
    private String username;
    @Column(name = "created_at")
    private Date createdAt;
}
