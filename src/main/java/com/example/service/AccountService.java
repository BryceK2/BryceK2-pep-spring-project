package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //register new account
    public Account registerAccount(Account account){
        if(account.getUsername().length() == 0 || account.getPassword().length() < 4){
            throw new ClientErrorException("Blank username or too short password provided.");
        } else if(accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateUsernameException("Username already exists.");
        } else return accountRepository.save(account); 
    }

    //login
    public Account loginAccount(Account account){
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

}
