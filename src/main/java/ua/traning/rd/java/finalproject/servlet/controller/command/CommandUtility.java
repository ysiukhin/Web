package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;

import javax.servlet.http.HttpServletRequest;

import java.util.HashSet;

import static ua.traning.rd.java.finalproject.Constants.ALL_LOGGED_ACCOUNTS;
import static ua.traning.rd.java.finalproject.Constants.LOGGED_ACCOUNT;

@SuppressWarnings("unchecked")
public
class CommandUtility {
    public static final Logger LOGGER = LogManager.getLogger(CommandUtility.class);


    public static void setUserRole(HttpServletRequest request,
                                   LoggedAccount.ROLE role, Account account) {
        LOGGER.info("IN CommandUtility: Create new LoggedAccount and add role --> ");
        HashSet<LoggedAccount> loggedAccounts = (HashSet<LoggedAccount>) request
                .getServletContext().getAttribute(ALL_LOGGED_ACCOUNTS);
        LoggedAccount newAccount = new LoggedAccount();
        newAccount.setAccount(account);
        newAccount.setRole(role);
        loggedAccounts.add(newAccount);
        request.getServletContext().setAttribute(ALL_LOGGED_ACCOUNTS, loggedAccounts);
        request.getSession().setAttribute(LOGGED_ACCOUNT, newAccount);
        LOGGER.info("OUT  CommandUtility: Create new LoggedAccount and add role --> ");
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String email) {
        LOGGER.info("IN CommandUtility: checkUserIsLogged");
        HashSet<LoggedAccount> loggedAccounts = (HashSet<LoggedAccount>) request.getSession().getServletContext()
                .getAttribute(ALL_LOGGED_ACCOUNTS);
        LOGGER.info("OUT CommandUtility: checkUserIsLogged");
        return loggedAccounts.stream().map(loggedAccount -> loggedAccount.getAccount().getEmail())
                .anyMatch(email::equals);
    }
}
