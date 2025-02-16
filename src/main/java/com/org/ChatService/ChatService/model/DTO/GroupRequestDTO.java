package com.org.ChatService.ChatService.model.DTO;

import com.org.ChatService.ChatService.model.User;
import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDTO
{
    private String groupName;
    private String description;
    private List<GroupUserDTO> groupUsers;

    public GroupRequestDTO(String groupName, String description, List<GroupUserDTO> groupUsers) {
        this.groupName = groupName;
        this.description = description;
        this.groupUsers = groupUsers;
    }
}
