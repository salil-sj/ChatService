package com.org.ChatService.ChatService.service.impl;

import com.org.ChatService.ChatService.model.DTO.GroupRequestDTO;
import com.org.ChatService.ChatService.model.DTO.GroupUserDTO;
import com.org.ChatService.ChatService.model.Group;
import com.org.ChatService.ChatService.model.GroupMembership;
import com.org.ChatService.ChatService.repository.GroupMembershipRepository;
import com.org.ChatService.ChatService.repository.GroupRepository;
import com.org.ChatService.ChatService.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService
{
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMembershipRepository groupMembershipRepository;

    @Override
    public void saveGroup(GroupRequestDTO groupRequestDTO)
    {
        Group group = new Group();
        List<GroupUserDTO> list = groupRequestDTO.getGroupUsers();
        group.setName(groupRequestDTO.getGroupName());
        group.setDescription(groupRequestDTO.getDescription());
        Group savedUser =  groupRepository.save(group);
        if(savedUser != null)
        {
            System.out.println("User Saved Successfully");
            Integer groupId = savedUser.getGroupId();

            list.stream().forEach(user ->{
                GroupMembership groupMembership = new GroupMembership();
                groupMembership.setGroupId(groupId);
                groupMembership.setUsername(user.getUserName());
                groupMembershipRepository.save(groupMembership);
            });

        }

    }
}
