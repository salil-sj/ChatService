package com.org.ChatService.ChatService.repository;

import com.org.ChatService.ChatService.model.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership , Integer> {
}
