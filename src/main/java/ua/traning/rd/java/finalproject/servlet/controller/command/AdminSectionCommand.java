package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AdminSectionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AdminSectionCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        LOGGER.info("IN AdminSectionCommand");
        String defaultPage = "/WEB-INF/admin/adminbasis.jsp";
        if (req.getSession().getAttribute("account") == null) {
            defaultPage = "redirect:/login.jsp";
            LOGGER.info("AdminSectionCommand: --> account = null -> redirect to {}", "redirect:/login.jsp");
        }

//        if (!account.getStatus()) {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.ADMIN, account);


        LOGGER.info("OUT AdminSectionCommand");
        return defaultPage;
//        } else if (account.getStatus()) {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.USER, account);
//            return /*redirect:*/"/WEB-INF/user/userbasis.jsp";
//        } else {
//            CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, account);
//            return "/login.jsp";
//        }


    }
}
