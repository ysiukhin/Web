package ua.traning.rd.java.finalproject.servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.traning.rd.java.finalproject.servlet.LoggedAccount;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LOGGER.info("Session {} account: {}", httpSessionEvent.getSession().getId(),
                httpSessionEvent.getSession().getServletContext().getAttribute("account"));

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

        HashSet<LoggedAccount> loggedAccounts = (HashSet<LoggedAccount>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedAccounts");
        LoggedAccount account = (LoggedAccount) httpSessionEvent.getSession()
                .getAttribute("account");
        loggedAccounts.remove(account);
        httpSessionEvent.getSession().getServletContext().setAttribute("loggedAccounts", loggedAccounts);
    }
}
