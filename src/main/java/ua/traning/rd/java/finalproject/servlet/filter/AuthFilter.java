package ua.traning.rd.java.finalproject.servlet.filter;

import ua.traning.rd.java.finalproject.core.model.LoggedAccount;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Objects.nonNull;
import static ua.traning.rd.java.finalproject.Constants.*;

//@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter extends AbstractFilter {
//    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        logger.info("AuthFilter URI: {}", req.getRequestURI());

        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(req.getSession().getAttribute(LANGUAGE))));


        String[] uri = req.getRequestURI().split("/");
        logger.info("URI: {}", Arrays.toString(uri));
        LoggedAccount currentAccount = ((LoggedAccount) req.getSession().getAttribute(LOGGED_ACCOUNT));
        if (nonNull(currentAccount)) {
            logger.info("Loged account role: {} requested section: {}", currentAccount.getRole(), uri[ACCESS_SECTION]);
            if (currentAccount.getRole()
                    .equals(LoggedAccount.ROLE
                            .valueOf(uri[ACCESS_SECTION].toUpperCase()))) {
                logger.info("currentAccount access granted");
                chain.doFilter(req, resp);
            } else {
                logger.warn("{}\n attempt to access {}", currentAccount.getAccount(), uri[ACCESS_SECTION].toUpperCase());
                logger.info("current account: {}", currentAccount);
                logger.info("req.sessionID: {}", req.getSession().getId());
//                req.getSession().invalidate();
//                loggedAccounts.stream().filter(saved->saved.getSessionId()
//                        .equals(account.sessionId)).forEach(loggedAccounts::remove);

//                resp.sendRedirect(req.getContextPath() + LOGIN_JSP);
                resp.sendRedirect(req.getContextPath() + "/" + currentAccount.getRole().name().toLowerCase());
            }
            logger.info("OUT AuthFilter URI: {}", req.getRequestURI());
        } else {
            logger.warn("Unauthorized access to protected area {}; -> REDIRECTING to login." +
                            "\nsession ID: {}\nIP: {}\nmethod: {}\nParameters: {}"
                    , req.getRequestURI(), req.getSession().getId(), req.getRemoteAddr()
                    , req.getMethod(), req.getParameterMap());

            req.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
            req.getSession().setAttribute(LAST_ACTION_STATUS, false);
            req.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, errorMessages.getString(MESSAGE_UNAUTHORIZED_ACCESS_SHORT));
            req.getSession().setAttribute(LAST_ACTION_MESSAGE_FULL, errorMessages.getString(MESSAGE_UNAUTHORIZED_ACCESS_FULL));
            HashMap<String, LoggedAccount> loggedAccounts = (HashMap<String, LoggedAccount>) req.getSession().getServletContext()
                    .getAttribute(ALL_LOGGED_ACCOUNTS);
            loggedAccounts.remove(req.getSession().getId(), currentAccount);
            req.getSession().removeAttribute(LOGGED_ACCOUNT);
            req.getSession().getServletContext().setAttribute(ALL_LOGGED_ACCOUNTS, loggedAccounts);
            resp.sendRedirect(req.getContextPath() + LOGIN_JSP);
        }
    }
}


