package com.extend.accounts.api;

import com.extend.accounts.model.Account;
import com.extend.accounts.service.AccountsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountsController {
    final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Account> getAccounts() {
        return accountsService.getAllAccounts();
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public Account createAccount(@RequestBody Account account) {
        return accountsService.createAccount(account);
    }
}
