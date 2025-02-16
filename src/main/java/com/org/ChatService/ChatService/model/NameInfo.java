package com.org.ChatService.ChatService.model;

import lombok.Data;

@Data
public class NameInfo
{
    private String firstName;
    private String lastName;

    public NameInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
