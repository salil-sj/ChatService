package com.org.ChatService.ChatService.repository;

import com.org.ChatService.ChatService.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group , Integer> {
}
