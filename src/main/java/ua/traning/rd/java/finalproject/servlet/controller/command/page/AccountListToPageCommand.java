package ua.traning.rd.java.finalproject.servlet.controller.command.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static ua.traning.rd.java.finalproject.Constants.*;

public class AccountListToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AccountListToPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountListToPageCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter(ROWS_PER_PAGE));
        Optional<String> pageNumber = Optional.ofNullable(request.getParameter(PAGE_NUMBER));

        int rowsPerPage = recordsPerPage.map(Integer::parseInt)
                .orElse((Integer) request.getAttribute(ROWS_PER_PAGE));

        int page = pageNumber.map(Integer::parseInt)
                .orElse((Integer) request.getSession().getAttribute(PAGE_NUMBER));

        List<Account> accounts;
        try {
            accounts = new EntityListService<>(Account.class)
                    .getInRangeByRowNumber(rowsPerPage, rowsPerPage * (page - 1));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString(EMPTY_RESULT));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }

        request.setAttribute(ACCOUNT_LIST, accounts);
        request.getSession().setAttribute(PAGE_NUMBER, page);
        request.setAttribute(ROWS_PER_PAGE, rowsPerPage);

        LOGGER.info("OUT AccountListToPageCommand");
        return ADMIN_ACCOUNT_LIST_JSP;
    }
}
