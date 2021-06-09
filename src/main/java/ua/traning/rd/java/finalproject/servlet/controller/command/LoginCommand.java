package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.LoginService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import ua.traning.rd.java.finalproject.core.model.LoggedAccount;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

import static ua.traning.rd.java.finalproject.Constants.*;

public class LoginCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("LoginCommand --> lang: {}", request.getSession().getAttribute(LANGUAGE));
//        LOGGER.info("In LoginCommand ");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        String email = request.getParameter(EMAIL);
        String password = request.getParameter("password");
        if (email == null || email.equals("") || password == null || password.equals("")) {
            //System.out.println("Not");
            return LOGIN_JSP;
        }

        if (CommandUtility.checkUserIsLogged(request, email)) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, errorMessages.getString("message.user.already.logged"));
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_FULL, errorMessages.getString("message.user.already.logged"));
            return Constants.LOGIN_JSP;
//            return "/login.jsp";
//            return "/WEB-INF/error.jsp";   // user loged
        }
        Account account;
        try {
            account = new LoginService().checkAccount(email, password);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString(MESSAGE_AUTHORIZATION_ERROR));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }


        if (!account.getStatus()) {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.ADMIN, account);
//            LOGGER.info("OUT LoginCommand -> redirect:/adminsection");
            return REDIRECT + ":" + COMMAND_ADMIN_SECTION;

        } else if (account.getStatus()) {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.USER, account);
//            LOGGER.info("OUT LoginCommand -> redirect:/usersection");
            return REDIRECT + ":" + COMMAND_USER_SECTION;
//            return Constants.USER_SECTION;

        } else {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, account);
            return LOGIN_JSP;
        }
    }
}
