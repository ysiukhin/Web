package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.LoginService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static ua.traning.rd.java.finalproject.Constants.*;

public class LoginCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("In LoginCommand --> lang: {}", request.getSession().getAttribute(LANGUAGE));
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        if (email == null || email.equals(EMPTY_STRING) || password == null || password.equals(EMPTY_STRING)) {
            request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, errorMessages.getString(MESSAGE_AUTHORIZATION_ERROR));
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_FULL, errorMessages.getString(MESSAGE_AUTHORIZATION_ERROR_FULL));
            return LOGIN_JSP;
        }
        if (CommandUtility.checkUserIsLogged(request, email)) {
            request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, errorMessages.getString(MESSAGE_ACCOUNT_ALREADY_IN_USE));
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_FULL, errorMessages.getString(MESSAGE_ACCOUNT_ALREADY_IN_USE_FULL));
            return LOGIN_JSP;
        }
        Account account;
        try {
            account = new LoginService().checkAccount(email, password);

            CommandUtility.setUserRole(request, account);
            request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT);
            LOGGER.info("Logging  account: {}\n session: {}", account.toString(), request.getSession(false).getId());
            return REDIRECT + ":" + (account.getStatus() ? COMMAND_USER_SECTION : COMMAND_ADMIN_SECTION);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, errorMessages.getString(MESSAGE_AUTHORIZATION_ERROR));
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_FULL, errorMessages.getString(MESSAGE_AUTHORIZATION_ERROR_FULL));
            return LOGIN_JSP;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
    }
}
