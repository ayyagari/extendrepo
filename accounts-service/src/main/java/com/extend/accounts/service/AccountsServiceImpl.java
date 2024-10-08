package com.extend.accounts.service;

import com.extend.accounts.model.Account;
import com.extend.accounts.persistence.AccountsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountsServiceImpl implements AccountsService {

    final AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountsRepository.findAll();
    }

    @Override
    public Account createAccount(Account account) {
        account.setId(UUID.randomUUID().toString());
        return accountsRepository.save(account);
    }
}
