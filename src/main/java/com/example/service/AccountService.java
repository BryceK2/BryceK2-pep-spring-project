package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //register new account
    public Account registerAccount(Account account){
        if(account.getUsername().length() == 0 || account.getPassword().length() < 4){
            //400
            return account;
        } else if(accountRepository.existsByUsername(account.getUsername())) {
            //409
            return account;
        } else return accountRepository.save(account); 
    }

    //login
    public Account loginAccount(Account account){
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

}
