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
import ua.traning.rd.java.finalproject.servlet.controller.command.LoginCommand;

import java.util.Optional;


public class CheckLoginService extends Service {
    public final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    public Account checkAccount(String email, String password) {
        LOGGER.info("IN CheckLoginService --> email:{}, password: {}", email, password);

        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(Servlet.dataSource);
        Dao<Account> accountDao =
                new DaoJdbc<>(sessionManagerJdbc, Account.class);
        DbService<Account> dbServiceAccount = new DbServiceImpl<>(accountDao);

        Optional<Account> account = dbServiceAccount.getBeansBy("email", email).map(list -> list.get(0));

        String savedPassword = account.map(Account::getMd5)
                .orElseThrow(() -> new ExceptionService(String
                        .format("There no account associated with email current: %s", email)));

        String providedPassword = ServiceUtils.getMd5(password);

        if (!savedPassword.equalsIgnoreCase(providedPassword)) {
            throw new ExceptionService(String
                    .format("Wrong password -> saved: [%s] and provided: [%s]", savedPassword, providedPassword));
        }
        LOGGER.info("OUT CheckLoginService --> return: {}", account.get());
        return account.get();
    }
}


