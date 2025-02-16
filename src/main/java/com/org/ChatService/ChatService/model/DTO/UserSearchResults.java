package com.org.ChatService.ChatService.model.DTO;

import lombok.Data;

@Data
public class UserSearchResults
{
    private String firstName;
    private String lastName;
    private String userName;
    private String email;

    public UserSearchResults(String userName, String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
    }
}
