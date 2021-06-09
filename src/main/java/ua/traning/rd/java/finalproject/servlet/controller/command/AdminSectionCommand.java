package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static ua.traning.rd.java.finalproject.Constants.LOGGED_ACCOUNT;
import static ua.traning.rd.java.finalproject.Constants.REDIRECT;

public class AdminSectionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AdminSectionCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.info("IN AdminSectionCommand");
        LOGGER.info("URI: {}", req.getRequestURI());
//        String defaultPage = Constants.ADMIN_SECTION;
        LoggedAccount account = (LoggedAccount) req.getSession().getAttribute(LOGGED_ACCOUNT);
        if (Objects.isNull(account)) {
            LOGGER.info("AdminSectionCommand: --> account = null -> redirect to login {}", REDIRECT + ":" + Constants.LOGIN_JSP);
            return REDIRECT + ":" + Constants.LOGIN_JSP;
        }
        if (account.getAccount().getStatus()) {
            LOGGER.info("AdminSectionCommand: --> account = USER -> redirect to userSection {}", REDIRECT + ":" + Constants.USER_SECTION);
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.USER, account);
//            return /*redirect:*/"/WEB-INF/user/userbasis.jsp";
        } else {
            LOGGER.info("AdminSectionCommand: --> account = ADMIN -> redirect to userSection {}", REDIRECT + ":" + Constants.USER_SECTION);
        }

//        else {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, account);
//            return "/login.jsp";
//        }
//        if (!account.getStatus()) {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.ADMIN, account);


        LOGGER.info("OUT AdminSectionCommand");
        return account.getAccount().getStatus() ? Constants.USER_SECTION : Constants.ADMIN_SECTION;
//        } else if (account.getStatus()) {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.USER, account);
//            return /*redirect:*/"/WEB-INF/user/userbasis.jsp";
//        } else {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, account);
//            return "/login.jsp";
//        }
    }
}
