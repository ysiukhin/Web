package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;

import javax.servlet.http.HttpServletRequest;

import static ua.traning.rd.java.finalproject.Constants.LOGGED_ACCOUNT;

public class AdminSectionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AdminSectionCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.info("IN AdminSectionCommand");
        LOGGER.info("URI: {}", req.getRequestURI());
        LoggedAccount account = (LoggedAccount) req.getSession().getAttribute(LOGGED_ACCOUNT);
        LOGGER.info("OUT AdminSectionCommand");
        return account.getAccount().getStatus() ? Constants.USER_SECTION : Constants.ADMIN_SECTION;
    }
}
