package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.core.dao.DbService;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AccountService {

    public final static Logger LOGGER = LogManager.getLogger(AccountService.class);

    public int totalQuantity() {
        LOGGER.info("IN AccountService --> totalQuantity()");
        List<Account> accounts = getAllAccounts();
        LOGGER.info("IN AccountService --> totalQuantity()");
        return accounts.size();
    }

    public List<Account> getAllAccounts() {
        LOGGER.info("IN AccountService --> getAllAccounts()");
        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(Servlet.dataSource);
        Dao<Account> accountDao =
                new DaoJdbc<>(sessionManagerJdbc, Account.class);
        DbService<Account> dbServiceAccount = new DbServiceImpl<>(accountDao);
        Optional<List<Account>> accounts = dbServiceAccount.getAllBeans();

        LOGGER.info("IN AccountService --> getAllAccounts()");
        return accounts.orElseThrow(() -> new ExceptionService("There is no any Accounts in database."));
    }

    public List<Account> getInRange(int rowsPerPage, int lastRow) {
        LOGGER.info("IN AccountService --> getInRange()");
        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(Servlet.dataSource);
        Dao<Account> accountDao =
                new DaoJdbc<>(sessionManagerJdbc, Account.class);
        DbService<Account> dbServiceAccount = new DbServiceImpl<>(accountDao);

        Optional<List<Account>> accounts = dbServiceAccount
                .getBeansInRange("id", Arrays.asList(lastRow, lastRow + rowsPerPage));

        LOGGER.info("IN AccountService --> getInRange()");
        return accounts.orElseThrow(() -> new ExceptionService("There is no any Accounts in database."));
    }

}
