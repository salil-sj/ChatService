package com.org.ChatService.ChatService.repository;

import com.org.ChatService.ChatService.model.Message;
import com.org.ChatService.ChatService.model.RecentMessages;
import com.org.ChatService.ChatService.utils.QueryConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>
{
    @Query(value=QueryConstants.RECENT_MESSAGES , nativeQuery = true)
    public List<List<Object>> getRecentMessage(String userName);


    @Query(value = QueryConstants.GET_USER_MESSAGE_HISTORY , nativeQuery = true)
    public List<List<Object>> getMessageHistory(String userA , String userB);


    @Query(value = QueryConstants.UPDATE_READ_QUERY)
    @Modifying
    public void updateReadFlag(String senderUserName , String receiverUserName);
}
