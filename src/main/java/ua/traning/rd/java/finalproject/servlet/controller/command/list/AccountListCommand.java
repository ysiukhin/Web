package ua.traning.rd.java.finalproject.servlet.controller.command.list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.core.service.EntityListServiceImpl;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.*;

public class AccountListCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AccountListCommand.class);
    private final EntityListService<Account> accountService;

    public AccountListCommand(EntityListService<Account> accountService) {
        this.accountService = accountService;
    }

    public AccountListCommand(DataSource dataSource) {
        this.accountService = new EntityListServiceImpl<>(Account.class, dataSource);
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountListCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));
        int rowsPerPage = Constants.DEFAULT_ROWS_PER_PAGE;
        int totalRecords = 0;
        try {
            totalRecords = accountService.totalEntityQuantity();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
        int pageCount = (int) Math.ceil((double) totalRecords / rowsPerPage);
        List<String> pagesLinks = new ArrayList<>();
        if (pageCount > 1) {
            for (int i = 0; i < Math.ceil((double) totalRecords / rowsPerPage); i++) {
                pagesLinks.add(String.format("%s?%s=%d&%s=%d", COMMAND_ADMIN_TO_PAGE_ACCOUNT, PAGE_NUMBER, i + 1, ROWS_PER_PAGE, rowsPerPage));
            }
        }
        if (Objects.isNull(request.getSession().getAttribute(PAGE_NUMBER)) ||
                Objects.isNull(request.getParameter(Constants.PAGE)) ||
                !(request.getParameter(Constants.PAGE).equals(request.getRequestURI()
                        .substring(request.getRequestURI().lastIndexOf('/') + 1)))) {
            request.getSession().setAttribute(PAGE_NUMBER, 1);
        }
        request.getSession().setAttribute(PAGINATION, pagesLinks);
        request.setAttribute(ROWS_PER_PAGE, rowsPerPage);

        LOGGER.info("OUT AccountListCommand");
        return COMMAND_ADMIN_TO_PAGE_ACCOUNT;
    }
}
