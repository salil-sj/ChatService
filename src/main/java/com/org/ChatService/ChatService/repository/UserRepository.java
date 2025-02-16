package com.org.ChatService.ChatService.repository;

import com.org.ChatService.ChatService.model.DTO.UserSearchResults;
import com.org.ChatService.ChatService.model.NameInfo;
import com.org.ChatService.ChatService.model.User;
import com.org.ChatService.ChatService.utils.QueryConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.phone_number=?2 ,u.password = ?3 where u.username =?1")
    public Integer updateUserProfileByUsername(String username,String phoneNumber ,String password);



     @Query(QueryConstants.USER_SEARCH_QUERY)
     public List<List<Object>> findUserByQueryString(String queryString);

     @Query(QueryConstants.GET_FULL_NAME_INFO)
     public NameInfo getFullNameInfoByUSerName(String userName);


}
