package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;

import javax.servlet.http.HttpServletRequest;

import static ua.traning.rd.java.finalproject.Constants.USER_SECTION;

public class UserSectionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(UserSectionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN UserSectionCommand");

//        if (!account.getStatus()) {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.ADMIN, account);

        LOGGER.info("OUT UserSectionCommand");
        return USER_SECTION;
//        return "/WEB-INF/user/userbasis.jsp";
//        } else if (account.getStatus()) {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.USER, account);
//            return /*redirect:*/"/WEB-INF/user/userbasis.jsp";
//        } else {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, account);
//            return "/login.jsp";
//        }


    }
}
