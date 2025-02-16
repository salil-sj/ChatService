package com.org.ChatService.ChatService.model;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "usergroup")
public class Group
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment primary key
    @Column(name="group_id")
    private Integer groupId;
    private String name;
    private String description;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    private Date updated_at;

    public Group() {
    }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Group(Integer groupId, String name, String description, Date created_at, Date updated_at) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}


/*
+-------------+--------------+------+-----+-------------------+-----------------------------------------------+
| Field       | Type         | Null | Key | Default           | Extra                                         |
+-------------+--------------+------+-----+-------------------+-----------------------------------------------+
| group_id    | int          | NO   | PRI | NULL              | auto_increment                                |
| name        | varchar(255) | NO   |     | NULL              |                                               |
| description | text         | YES  |     | NULL              |                                               |
| created_at  | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED                             |
| updated_at  | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED on update CURRENT_TIMESTAMP |
+-------------+--------------+------+-----+-------------------+-----------------------------------------------+
 */