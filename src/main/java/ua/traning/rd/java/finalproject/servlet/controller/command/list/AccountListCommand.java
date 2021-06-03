package ua.traning.rd.java.finalproject.servlet.controller.command.list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.ExceptionService;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class AccountListCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AccountListCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountListCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        int rowsPerPage = Constants.ROWS_PER_PAGE;

        int totalRecords = 0;

        EntityListService<Account> accountService = new EntityListService<>(Account.class);

        try {
            totalRecords = accountService.totalQuantity();
        } catch (ExceptionService e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.request.data.empty"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }
        int pageCount = (int) Math.ceil((double) totalRecords / rowsPerPage);
        List<String> pagesLinks = new ArrayList<>();
        if (pageCount > 1) {
            for (int i = 0; i < Math.ceil((double) totalRecords / rowsPerPage); i++) {
                pagesLinks.add(String.format("/topageaccount?pagenumber=%d&rowsPerPage=%d", i + 1, rowsPerPage));
            }
        }
        if (Objects.isNull(request.getSession().getAttribute("pagenumber")) ||
                Objects.isNull(request.getParameter("page")) ||
                !(request.getParameter("page").equals(request.getRequestURI()
                        .substring(request.getRequestURI().lastIndexOf('/') + 1)))) {
            request.getSession().setAttribute("pagenumber", 1);
        }
//        if(Objects.nonNull(request.getSession().getAttribute("pagenumber")) &&
//                Objects.nonNull(request.getParameter("page")) &&
//                request.getParameter("page").equals(request.getRequestURI()
//                        .substring(request.getRequestURI().lastIndexOf('/')+1))) {
//            request.getSession().setAttribute("pagenumber", request.getParameter("page"));
//        }else {
//            request.getSession().setAttribute("pagenumber", 1);
//        }

        request.getSession().setAttribute("pages", pagesLinks);
        request.setAttribute("rowsPerPage", rowsPerPage);

        LOGGER.info("OUT AccountListCommand");
        return "/topageaccount";
    }
}
