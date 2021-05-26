package ua.traning.rd.java.finalproject.servlet.controller.command;

import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.servlet.LoggedAccount;

import javax.servlet.http.HttpServletRequest;

import java.util.HashSet;

class CommandUtility {
    static void setUserRole(HttpServletRequest request,
                            LoggedAccount.ROLE role, Account account) {

        HashSet<LoggedAccount> loggedAccounts = (HashSet<LoggedAccount>) request
                .getServletContext().getAttribute("loggedAccounts");
        LoggedAccount newAccount = new LoggedAccount();
        newAccount.setAccount(account);
        newAccount.setRole(role);
        loggedAccounts.add(newAccount);
        request.getServletContext().setAttribute("loggedAccounts", loggedAccounts);
        request.getSession().setAttribute("account", newAccount);
    }

    static boolean checkUserIsLogged(HttpServletRequest request, String email) {

        HashSet<LoggedAccount> loggedAccounts = (HashSet<LoggedAccount>) request.getSession().getServletContext()
                .getAttribute("loggedAccounts");

        return loggedAccounts.stream().map(loggedAccount -> loggedAccount.getAccount().getEmail())
                .anyMatch(email::equals);
    }
}
