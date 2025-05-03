package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ClientErrorException;
import com.example.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {
    
    //Dependency injection
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    //Adds a new message to the database after validating input
    public Message addMessage(Message message){
        //Checks if message is not blank and is under 255 characters long, throw 400 if invalid message
        if(message.getMessageText().length() == 0 || message.getMessageText().length() > 255){
            throw new ClientErrorException("Message text invalid format.");
        } 
        //Checks if postedBy is a real user, throw 400 if invalid user
        else if (!messageRepository.existsByPostedBy(message.getPostedBy())) {
            throw new ClientErrorException("Invalid user attempting to add message.");
        } 
        //Saves and returns newly added message
        else {
            return messageRepository.save(message);
        }
    }

    //Gets all messages from the database
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //Gets message by messageId from database, or returns null if it doesn't exist
    public Message getMessageByMessageId(int messageId){
        return messageRepository.findById(messageId).orElse(null);
    }

    //Deletes message by messageId from database, returns number of rows deleted
    public int deleteMessageByMessageId(int messageId){
        return messageRepository.deleteMessageById(messageId);
    }

    //Updates message by messageId, returns number of rows updated
    public int patchMessageByMessageId(Message message, int messageId){
        //Attempt to retrieve message by provided input messageId
        Message validMessage = getMessageByMessageId(messageId);

        //Check if message valid, throw 400 if invalid
        if(validMessage == null){
            throw new ClientErrorException("Message with inputted messageId does not exist in database.");
        } 
        //Checks if message is not blank and is under 255 characters long, throw 400 if invalid message
        else if(message.getMessageText().length() == 0 || message.getMessageText().length() > 255) {
            throw new ClientErrorException("Message text invalid format.");
        } 
        //Update message with inputted message text and return updated message
        else {
            return messageRepository.patchMessageById(message.getMessageText(), messageId);
        }
    }

    //Get all messages written by inputted accountId
    public List<Message> getAllMessagesByAccountId(int accountId) {
        //Finds and returns message associated with input by 'postedBy'
        //This works because postedBy is foreign key referencing Account.accountId
        return messageRepository.findByPostedBy(accountId);
    }
}
