package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocialMediaController {

    //Dependency Injection
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //Registers a new account using provided Account object
    @PostMapping("/register")
    public ResponseEntity<Account> postCreateAccount(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        return ResponseEntity.ok(registeredAccount);
    }

    //Logs user in if user inputted credentials valid
    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account) {
        Account validAccount = accountService.loginAccount(account);
        return ResponseEntity.ok(validAccount);
    }

    //Creates and saves new message provided by user
    @PostMapping("/messages")
    public ResponseEntity<Message> postNewMessage(@RequestBody Message message) {
        Message savedMessage = messageService.addMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    //Gets all messages from database
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messageList = messageService.getAllMessages();
        return ResponseEntity.ok(messageList);
    }

    //Gets specific message by messageId
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Message message = messageService.getMessageByMessageId(messageId); 
        if(message == null) return ResponseEntity.ok().build();
        return ResponseEntity.ok(message);
    }

    //Deletes message by messageId
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        int rowsDeleted = messageService.deleteMessageByMessageId(messageId);

        //Returns 1 + 200 status if deletion successful
        if(rowsDeleted == 1) return ResponseEntity.ok(rowsDeleted);
        //Returns empty + 200 status if no deletion occurs
        else return ResponseEntity.status(200).build();
    }

    //Updates message by messageId if valid message text
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessageById(@RequestBody Message message, @PathVariable int messageId) {
        int rowsUpdated = messageService.patchMessageByMessageId(message, messageId);
        return ResponseEntity.ok(rowsUpdated);
    }

    //Gets all messages posted by specified account
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId) {
        List<Message> messageList = messageService.getAllMessagesByAccountId(accountId);
        return ResponseEntity.ok(messageList);
    }

    //Exception Handler: Client error (400)
    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<String> handleClientErrorException(ClientErrorException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    //Exception Handler: Unauthorized (401)
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<String> handleUnauthorizedUserException(UnauthorizedUserException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }

    //Exception Handler: Duplicate username conflict (409)
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsername(DuplicateUsernameException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }
}
