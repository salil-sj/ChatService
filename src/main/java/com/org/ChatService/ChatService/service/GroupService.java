package com.org.ChatService.ChatService.service;

import com.org.ChatService.ChatService.model.DTO.GroupRequestDTO;
import com.org.ChatService.ChatService.model.Group;

public interface GroupService
{
    public void saveGroup(GroupRequestDTO group);
}

/*
| Field       | Type         | Null | Key | Default           | Extra                                         |
+-------------+--------------+------+-----+-------------------+-----------------------------------------------+
| group_id    | int          | NO   | PRI | NULL              | auto_increment                                |
| name        | varchar(255) | NO   |     | NULL              |                                               |
| description | text         | YES  |     | NULL              |                                               |
| created_at  | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED                             |
| updated_at  | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED on update CURRENT_TIMESTAMP |
+-------------+--------------+------+-----+-------------------+-----------------------------------------------+
 */