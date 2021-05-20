package com.epam.rd.java.finalproject.core.service;

import com.epam.rd.java.finalproject.core.model.Account;

import java.util.List;
import java.util.Optional;

public interface DbServiceAccount {
    Optional<List<Account>> getAllAccounts();

    Optional<Account> getAccount(int id);

    int saveAccount(Account account);

    int updateAccount(Account account);
}
