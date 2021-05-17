package com.epam.rd.java.finalproject.core.service;

import com.epam.rd.java.finalproject.core.model.Account;

import java.util.List;
import java.util.Optional;

public abstract class DbServiceAccount {
    public abstract Optional<List<Account>> getAllAccounts();

    public abstract Optional<Account> getAccount(int id);

    public abstract int saveAccount(Account account);
}
