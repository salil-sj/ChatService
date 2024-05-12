package com.org.ChatService.ChatService.repository;

import com.org.ChatService.ChatService.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
