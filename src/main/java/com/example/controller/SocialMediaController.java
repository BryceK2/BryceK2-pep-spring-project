package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> postCreateAccount(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        if(1 == 1) return ResponseEntity.ok(account);
        //if registration unsuccessful due to duplicate username
        else if(2 == 2) return ResponseEntity.status(409).build();
        //if registration unsuccessful due to other reason
        else return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account) {
        Account existingAccount = accountService.loginAccount(account);
        if(existingAccount != null) return ResponseEntity.ok(account);
        //if login unsuccessful
        else return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postNewMessage(@RequestBody Message message) {
        Message savedMessage = messageService.addMessage(message);
        if(savedMessage != null) return ResponseEntity.ok(savedMessage);
        //if message creation unsuccessful
        else return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messageList = messageService.getAllMessages();
        return ResponseEntity.ok(messageList);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessageByMessageId(messageId); 
        return ResponseEntity.of(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        int rowsDeleted = messageService.deleteMessageByMessageId(messageId);

        if(rowsDeleted == 1) return ResponseEntity.ok(rowsDeleted);
        else return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessageById(@RequestBody Message message, @PathVariable int messageId) {
        int rowsUpdated = messageService.patchMessageByMessageId(message, messageId);
        
        if(rowsUpdated > 0) return ResponseEntity.ok(rowsUpdated);
        else return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId) {
        List<Message> messageList = messageService.getAllMessagesByAccountId(accountId);
        return ResponseEntity.ok(messageList);
    }

}
