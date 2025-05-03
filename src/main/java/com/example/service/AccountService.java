package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.UnauthorizedUserException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    //Dependency injection
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //Registers new account after validating input
    public Account registerAccount(Account account){
        //Checks if username is blank or password is shorter than 4 characters, throw 400 if true
        if(account.getUsername().length() == 0 || account.getPassword().length() < 4){
            throw new ClientErrorException("Blank username or too short password provided.");
        } 
        //Checks if username is already taken, throw 409 if true
        else if(accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateUsernameException("Username already exists.");
        } 
        //Saves and returns new account
        else return accountRepository.save(account); 
    }

    //Login user if valid credentials
    public Account loginAccount(Account account){
        //Grabs account associated with inputted credentials
        Account validAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        
        //Checks if account returned is null, thereby invalid credentials, throw 401 if unauthorized
        if(validAccount == null) {
            throw new UnauthorizedUserException("Invalid credentials.");
        } 
        //Return matching account if valid
        else {
            return validAccount;
        }
    }
}
