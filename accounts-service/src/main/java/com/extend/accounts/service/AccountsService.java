package com.extend.accounts.service;

import com.extend.accounts.model.Account;

import java.util.List;

public interface AccountsService {
    List<Account> getAllAccounts();
    Account createAccount(Account account);
}
