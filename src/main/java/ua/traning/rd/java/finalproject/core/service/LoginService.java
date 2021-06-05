package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;
import ua.traning.rd.java.finalproject.servlet.controller.command.LoginCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import java.util.Optional;


public class LoginService {
    public final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    public Account checkAccount(String email, String password) {
        LOGGER.info("IN LoginService");
        Optional<Account> account = new DbServiceImpl<>(new DaoJdbc<>(
                new SessionManagerJdbc(Servlet.dataSource), Account.class))
                .getBeansBy("email", email).map(list -> list.get(0));

        String savedPassword =
                account.map(Account::getMd5).orElseThrow(() ->
                        new ServiceException(String.format("There no account associated with email current: %s", email)));

        String providedPassword = ServiceUtils.getMd5(password);

        if (!savedPassword.equalsIgnoreCase(providedPassword)) {
            throw new ServiceException(String
                    .format("Wrong password -> saved: [%s] and provided: [%s]", savedPassword, providedPassword));
        }
        LOGGER.info("OUT LoginService");
        return account.get();
    }
}


