package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Register a new account
     * @param account The account to register
     * @return The registered account with its ID, or null if registration fails
     */
    public Account registerAccount(Account account) {
        // Check if username already exists
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null) {
            return null; // Username already exists
        }
        
        // Save the new account
        return accountRepository.save(account);
    }

    /**
     * Login with username and password
     * @param username The username
     * @param password The password
     * @return The authenticated account, or null if authentication fails
     */
    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }
}
