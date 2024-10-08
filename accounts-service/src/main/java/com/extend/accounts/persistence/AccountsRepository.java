package com.extend.accounts.persistence;

import com.extend.accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account, String> {
}
