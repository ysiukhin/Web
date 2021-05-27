package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.LoginService;
import ua.traning.rd.java.finalproject.core.service.ExceptionService;

import ua.traning.rd.java.finalproject.servlet.LoggedAccount;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;


import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("LoginCommand --> lang: {}", request.getSession().getAttribute("lang"));
        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || email.equals("") || password == null || password.equals("")) {
            //System.out.println("Not");
            return "/login.jsp";
        }

        email = new String(email.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        password = new String(password.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        if (CommandUtility.checkUserIsLogged(request, email)) {
            return "/WEB-INF/error.jsp";   // user loged
        }

        LOGGER.info("email: {}, password: {}", email, password);

        Account account;
        try {
            account = new LoginService().checkAccount(email, password);
        } catch (ExceptionService e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.authorization.failed"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }

        if (!account.getStatus()) {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.ADMIN, account);
            return "redirect:/adminsection";
//            return "redirect:/adminsection";
        } else if (account.getStatus()) {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.USER, account);
            return "redirect:/usersection";
//            return "redirect:/usersection";
        } else {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, account);
            return "/login.jsp";
        }
    }
}
