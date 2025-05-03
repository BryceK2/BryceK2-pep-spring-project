package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    //get all messages
    List<Message> findMessages();

    //get Message by messageId
    Message findMessageByMessageId(int messageId);
}
