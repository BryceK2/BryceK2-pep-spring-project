package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    //adds message to db
    public Message addMessage(Message message){
        return messageRepository.save(message);
    }

    //get all messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //get message by messageId
    public Optional<Message> getMessageById(int messageId){
        return messageRepository.findById(messageId);
    }
}
