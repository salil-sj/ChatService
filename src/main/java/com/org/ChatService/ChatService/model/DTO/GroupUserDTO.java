package com.org.ChatService.ChatService.model.DTO;

import lombok.Data;

@Data
public class GroupUserDTO
{
    private String userName;
    private String email;
    private String firstName;
    private String lastName;

    public GroupUserDTO(String userName) {
        this.userName = userName;
    }

    public GroupUserDTO(String userName, String email, String firstName, String lastName) {
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
