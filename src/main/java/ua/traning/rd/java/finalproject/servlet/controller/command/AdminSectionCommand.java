package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.core.service.ExceptionService;
import ua.traning.rd.java.finalproject.servlet.LoggedAccount;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminSectionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AdminSectionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AdminSectionCommand");
//        if (!account.getStatus()) {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.ADMIN, account);

        LOGGER.info("OUT AdminSectionCommand");
        return "/WEB-INF/admin/adminbasis.jsp";
//        } else if (account.getStatus()) {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.USER, account);
//            return /*redirect:*/"/WEB-INF/user/userbasis.jsp";
//        } else {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, account);
//            return "/login.jsp";
//        }


    }
}
