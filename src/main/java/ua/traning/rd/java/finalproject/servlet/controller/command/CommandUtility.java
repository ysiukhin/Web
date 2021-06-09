package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.HashSet;

import static ua.traning.rd.java.finalproject.Constants.ALL_LOGGED_ACCOUNTS;
import static ua.traning.rd.java.finalproject.Constants.LOGGED_ACCOUNT;

@SuppressWarnings("unchecked")
public
class CommandUtility {
    public static final Logger LOGGER = LogManager.getLogger(CommandUtility.class);


    public static void setUserRole(HttpServletRequest request, Account account) {
        LOGGER.info("IN CommandUtility: Create new LoggedAccount and add role --> ");
        HashMap<String, LoggedAccount> loggedAccounts = (HashMap<String, LoggedAccount>) request
                .getServletContext().getAttribute(ALL_LOGGED_ACCOUNTS);
        loggedAccounts.computeIfPresent(request.getSession().getId(), (key, value) -> {
                    value.setRole(account.getStatus() ? LoggedAccount.ROLE.USER : LoggedAccount.ROLE.ADMIN);
                    value.setAccount(account);
                    return value;
                }
        );
        loggedAccounts.computeIfAbsent(request.getSession().getId(), key -> {
                    LoggedAccount newAccount = new LoggedAccount();
                    newAccount.setRole(account.getStatus() ? LoggedAccount.ROLE.USER : LoggedAccount.ROLE.ADMIN);
                    newAccount.setAccount(account);
                    return newAccount;
                }
        );
//        LoggedAccount newAccount = new LoggedAccount();
//        newAccount.setAccount(account);
//        newAccount.setRole(account.getStatus() ? LoggedAccount.ROLE.USER : LoggedAccount.ROLE.ADMIN);
//        newAccount.setSessionId(request.getSession().getId());

        request.getServletContext().setAttribute(ALL_LOGGED_ACCOUNTS, loggedAccounts);
        request.getSession().setAttribute(LOGGED_ACCOUNT, loggedAccounts.get(request.getSession().getId()));

        LOGGER.info("OUT  CommandUtility: Create new LoggedAccount and add role --> ");
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String email) {
        LOGGER.info("IN CommandUtility: checkUserIsLogged");
        HashMap<String, LoggedAccount> loggedAccounts = (HashMap<String, LoggedAccount>)
                request.getSession().getServletContext().getAttribute(ALL_LOGGED_ACCOUNTS);
        LOGGER.info("OUT CommandUtility: checkUserIsLogged");
//        loggedAccounts.values().stream().map(loggedAccount -> loggedAccount.getAccount().getEmail())
//                .anyMatch(email::equals);
        return loggedAccounts.values().stream().map(loggedAccount -> loggedAccount.getAccount().getEmail())
                .anyMatch(email::equals);
    }
}
