package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    boolean existsByPostedBy(int postedBy);

    @Query("DELETE m FROM m WHERE m.messageId = :messageId")
    int deleteMessageById(@Param("messageId") int messageId);

    @Query("UPDATE m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    int patchMessageById(@Param("messageText") String messageText, @Param("messageId") int messageId);

    @Query("SELECT m FROM m WHERE m.postedBy = : accountId")
    List<Message> findMessagesByAccountId(@Param("accountId") int accountId);
}
