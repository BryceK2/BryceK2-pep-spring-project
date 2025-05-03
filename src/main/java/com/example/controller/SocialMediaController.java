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
        return ResponseEntity.ok(registeredAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account) {
        Account validAccount = accountService.loginAccount(account);
        return ResponseEntity.ok(validAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postNewMessage(@RequestBody Message message) {
        Message savedMessage = messageService.addMessage(message);
        return ResponseEntity.ok(savedMessage);
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
        return ResponseEntity.ok(rowsUpdated);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId) {
        List<Message> messageList = messageService.getAllMessagesByAccountId(accountId);
        return ResponseEntity.ok(messageList);
    }

    //Exception Handler: Client error (400)
    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<String> ClientErrorException(DuplicateUsernameException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    //Exception Handler: Unauthorized (401)
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<String> UnauthorizedUserException(DuplicateUsernameException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }

    //Exception Handler: Duplicate username conflict (409)
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsername(DuplicateUsernameException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

}
