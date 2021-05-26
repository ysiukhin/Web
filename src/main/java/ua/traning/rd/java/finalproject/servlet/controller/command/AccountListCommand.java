package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.traning.rd.java.finalproject.core.service.ExceptionService;
import ua.traning.rd.java.finalproject.core.service.AccountService;
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


//        String recordsPerPage = request.getParameter("rowsPerPage");
//        String lastIndex = request.getParameter("lastIndex");
//
//        int lastRecord = 0;
//        if (lastIndex!=null){
//            lastRecord = Integer.parseInt(lastIndex);
//        }
//
        int rowsPerPage = 10;
//        if (recordsPerPage!=null){
//            rowsPerPage = Integer.parseInt(lastIndex);
//        }
//
        int totalRecords = 0;

//        List<Account> accounts;
        AccountService accountService = new AccountService();
        try {
            totalRecords = accountService.totalQuantity();
//            accounts = new AccountService().getInRange(10, 0);
        } catch (ExceptionService e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.request.data.empty"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }

        Map<String, String> pagesLinks = new HashMap<>();
        for (int i = 0; i < totalRecords / rowsPerPage; i++) {
//            String link = String.format("/topageaccount?lastIndex=%d",i+1);
            pagesLinks.put(String.valueOf(i), String.format("/topageaccount?lastIndex=%d", i + 1));
        }

        request.setAttribute("pagenumber", 1);
//        request.setAttribute("lastIndex", lastRecord + 10);
        request.setAttribute("pagesLinks", pagesLinks);
        request.setAttribute("rowsPerPage", rowsPerPage);

        LOGGER.info("OUT AccountListCommand");
        return "/topageaccount";
    }
}
