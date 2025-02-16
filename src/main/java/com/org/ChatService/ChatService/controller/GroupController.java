package com.org.ChatService.ChatService.controller;

import com.org.ChatService.ChatService.model.DTO.GroupRequestDTO;
import com.org.ChatService.ChatService.model.Group;
import com.org.ChatService.ChatService.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/createGroup")
    public ResponseEntity createGroup(@RequestBody GroupRequestDTO groupRequestDTO)
    {
        groupService.saveGroup(groupRequestDTO);
        return ResponseEntity.ok("Group created succesfully!!");

    }
}
