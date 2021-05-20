package com.epam.rd.java.finalproject.core.service;

import com.epam.rd.java.finalproject.core.dao.AbstractDao;
import com.epam.rd.java.finalproject.core.model.Account;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import com.epam.rd.java.finalproject.core.dao.AccountSql;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DbServiceAccountImpl implements DbServiceAccount {

    private static final Logger logger = LogManager.getLogger(DbServiceAccountImpl.class);

    private final AbstractDao<Account> accountDao;

    public DbServiceAccountImpl(AbstractDao<Account> accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Optional<List<Account>> getAllAccounts() {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<List<Account>> allAccounts = accountDao.select();
                logger.debug("Account: {}", allAccounts.orElse(null));
                return allAccounts;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Account> getAccount(int id) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> account = accountDao.selectByField(id);
                logger.debug("Account: {}", account.orElse(null));
                return account;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public int saveAccount(Account account) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                int accountId = accountDao.insert(account);
                sessionManager.commitSession();
                logger.debug("created account: {}", accountId);
                return accountId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public int updateAccount(Account account) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                int accountId = accountDao.update(account);
                sessionManager.commitSession();
                logger.debug("updated account: {}", accountId);
                return accountId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }
}