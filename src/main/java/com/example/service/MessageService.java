package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    //adds message to db
    public Message addMessage(Message message){
        //checks if message is not blank and is not over 255 characters long, and postedBy is real user
        if(message.getMessageText().length() > 0 && message.getMessageText().length() <= 255 && messageRepository.existsByPostedBy(message.getPostedBy())) {
            return messageRepository.save(message);
        } else {
            throw new ClientErrorException("Message text invalid format or invalid user.");
        }
    }

    //get all messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //get message by messageId
    public Optional<Message> getMessageByMessageId(int messageId){
        return messageRepository.findById(messageId);
    }

    //delete message by messageId
    public int deleteMessageByMessageId(int messageId){
        return messageRepository.deleteMessageById(messageId);
    }

    //patch message by messageId
    public int patchMessageByMessageId(Message message, int messageId){
        String messageText = message.getMessageText();
        Optional<Message> validMessage = getMessageByMessageId(messageId);
        
        if(validMessage != null && messageText.length() > 0 && messageText.length() <= 255){
            return messageRepository.patchMessageById(messageText, messageId);
        } else {
            throw new ClientErrorException("Message text invalid format or messageId does not exist in Database.");
        }
    }

    //get all messages written by Account.accountId
    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageRepository.findMessagesByAccountId(accountId);
    }
}
