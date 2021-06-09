package ua.traning.rd.java.finalproject.servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.traning.rd.java.finalproject.core.model.LoggedAccount;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

import static ua.traning.rd.java.finalproject.Constants.ALL_LOGGED_ACCOUNTS;
import static ua.traning.rd.java.finalproject.Constants.LOGGED_ACCOUNT;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LOGGER.info("Session created  {} \nloggedAccounts: {}", httpSessionEvent.getSession().getId(),
                httpSessionEvent.getSession().getServletContext().getAttribute(ALL_LOGGED_ACCOUNTS));

//        LOGGER.info("SessionListener:sessionCreated --> Session {} account: {}",
//                httpSessionEvent.getSession().getId(),
//                httpSessionEvent.getSession().getServletContext().getAttribute("loggedAccounts"));

    }

    @SuppressWarnings("unchecked")
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        HashSet<LoggedAccount> loggedAccounts = (HashSet<LoggedAccount>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute(ALL_LOGGED_ACCOUNTS);
        LoggedAccount account = (LoggedAccount) httpSessionEvent.getSession()
                .getAttribute(LOGGED_ACCOUNT);
        loggedAccounts.remove(account);
        httpSessionEvent.getSession().removeAttribute(LOGGED_ACCOUNT);
        httpSessionEvent.getSession().getServletContext().setAttribute(ALL_LOGGED_ACCOUNTS, loggedAccounts);
        LOGGER.info("Session Destroyed --> {} account: {}", httpSessionEvent.getSession().getId(),
                httpSessionEvent.getSession().getServletContext().getAttribute(ALL_LOGGED_ACCOUNTS));
    }
}
