package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.LoginService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import ua.traning.rd.java.finalproject.core.model.LoggedAccount;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
//        LOGGER.info("LoginCommand --> lang: {}", request.getSession().getAttribute("lang"));
//        LOGGER.info("In LoginCommand ");
        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || email.equals("") || password == null || password.equals("")) {
            //System.out.println("Not");
            return "/login.jsp";
        }
        if (CommandUtility.checkUserIsLogged(request, email)) {

            return "/WEB-INF/error.jsp";   // user loged
        }
        Account account;
        try {
            account = new LoginService().checkAccount(email, password);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.authorization.failed"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }


        if (!account.getStatus()) {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.ADMIN, account);
//            LOGGER.info("OUT LoginCommand -> redirect:/adminsection");
            return "redirect:/adminsection";

        } else if (account.getStatus()) {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.USER, account);
//            LOGGER.info("OUT LoginCommand -> redirect:/usersection");
            return "redirect:/usersection";

        } else {
            CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, account);
//            LOGGER.info("OUT LoginCommand -> /usersection");
            return "/login.jsp";
        }
    }
}
