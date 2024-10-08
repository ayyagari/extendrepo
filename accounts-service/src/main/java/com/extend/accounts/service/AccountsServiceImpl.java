package com.extend.accounts.service;

import com.extend.accounts.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountsRepositoryImpl implements AccountsRepository {
    @Override
    public List<Account> getAllAccounts() {
        return List.of(
                new Account(UUID.randomUUID().toString(), "05b9aa99-e832-43a7-a802-9cef5d58c3ab"),
                new Account(UUID.randomUUID().toString(), "05b9aa99-e832-43a7-a802-9cef5d58c3ab"),
                new Account(UUID.randomUUID().toString(), "69f11be2-a155-46a4-8022-35b02ad408f0")
        );
    }
}
