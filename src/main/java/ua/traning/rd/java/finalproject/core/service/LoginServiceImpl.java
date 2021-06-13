package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.DbService;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.controller.command.LoginCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import javax.sql.DataSource;

import static ua.traning.rd.java.finalproject.Constants.EMAIL;


public class LoginServiceImpl implements LoginService {
    public final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private DbService<Account> dbService;

    public LoginServiceImpl(DbServiceImpl<Account> dbService) {
        this.dbService = dbService;
    }

    public LoginServiceImpl(DataSource dataSource) {
        this.dbService = new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), Account.class));
    }

    public Account checkAccount(String email, String password) {
        LOGGER.info("IN LoginService");
        Account account = dbService.getBeansBy(EMAIL, email).get(0);
        String savedPassword = account.getMd5();
        String providedPassword = ServiceUtils.getMd5(password);
        if (!savedPassword.equalsIgnoreCase(providedPassword)) {
            throw new ServiceException(String
                    .format("Wrong password -> saved: [%s] and provided: [%s]", savedPassword, providedPassword));
        }
        LOGGER.info("OUT LoginService");
        return account;
    }
}


