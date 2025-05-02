package com.example.controller;

import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @PostMapping("/register")
    public ResponseEntity<Account> postCreateAccount(@RequestBody Account account) {
        //if success TODO**
        if(1 == 1) return ResponseEntity.ok(account);
        //if registration unsuccessful due to duplicate username
        else if(2 == 2) return ResponseEntity.status(409);
        //if registration unsuccessful due to other reason
        else return ResponseEntity.status(400);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account) {
        //if success TODO**
        if(1 == 1) return ResponseEntity.ok(account);
        //if login unsuccessful
        else return ResponseEntity.status(401);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postNewMessage(@RequestBody Message message) {
        //if success TODO**
        if(1 == 1) return ResponseEntity.ok(message);
        //if message creation unsuccessful
        else return ResponseEntity.status(400);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        //if success TODO**
        return ResponseEntity.ok(messageList);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        //if success TODO**
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        //add logic for retrieving num updated rows
        int rowsUpdated = 0;

        if(rowsUpdated == 1) return ResponseEntity.ok(rowsUpdated);
        else return ResponseEntity.status(200).build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessageById(@RequestBody Message message, @PathVariable int messageId) {
        //add logic for retrieving num updated rows
        int rowsUpdated = 0;
        
        if(rowsUpdated == 1) return ResponseEntity.ok(rowsUpdated);
        else return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getAllMessagesByAccountId(@PathVariable int accountId) {
        List<Message> messageList = new ArrayList<>();
        //if success TODO**
        return ResponseEntity.ok(messageList);
    }

}
