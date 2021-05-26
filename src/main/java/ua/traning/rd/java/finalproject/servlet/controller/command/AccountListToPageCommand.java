package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.AccountService;
import ua.traning.rd.java.finalproject.core.service.ExceptionService;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AccountListToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AccountListToPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountListToPageCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        String recordsPerPage = request.getParameter("rowsPerPage");
        String pagenumber = request.getParameter("pagenumber");

        LOGGER.info("rowsPerPage: {} pagenumber: {}", recordsPerPage, pagenumber);
        int rowsPerPage = Integer.parseInt(request.getParameter("rowsPerPage"));
        int page = Integer.parseInt(request.getParameter("pagenumber"));


        List<Account> accounts;
        try {
            accounts = new AccountService().getInRange((page - 1) * rowsPerPage, page * rowsPerPage);
        } catch (ExceptionService e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.request.data.empty"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }

        request.setAttribute("accounts", accounts);
//        request.setAttribute("lastIndex", lastRecord + 10);
//        request.setAttribute("rowsPerPage", rowsPerPage);


        LOGGER.info("rowsPerPage: {} page: {}", rowsPerPage, page);
        LOGGER.info("OUT AccountListToPageCommand");
        return "/WEB-INF/admin/accountlist.jsp";
    }
}
