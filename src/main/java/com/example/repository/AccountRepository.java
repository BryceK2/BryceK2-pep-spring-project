package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    //Checks if account with specified username exists, returns boolean
    boolean existsByUsername(String username);

    //Finds an account with inputted username and password and returns it
    Account findByUsernameAndPassword(String username, String password);
}
