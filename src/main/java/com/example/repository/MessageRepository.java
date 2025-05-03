package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    //Checks if any messages exist by inputted id
    boolean existsByPostedBy(int postedBy);

    //Deletes message from database based on message id
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    int deleteMessageById(@Param("messageId") int messageId);

    //Updates message text based on inputted message id
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    int patchMessageById(@Param("messageText") String messageText, @Param("messageId") int messageId);

    //Gets all messages from specified user
    List<Message> findByPostedBy(int postedBy);
}
