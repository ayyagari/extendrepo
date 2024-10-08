package com.extend.accounts.api;

import com.extend.accounts.model.Account;
import com.extend.accounts.service.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountsController {
    final AccountsRepository accountsRepository;

    public AccountsController(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @GetMapping()
    public List<Account> getAccounts() {
        return accountsRepository.getAllAccounts();
    }

}
