package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;
import ua.traning.rd.java.finalproject.servlet.controller.command.page.RequestListToPageCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.*;

public class UserRequestListToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestListToPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN RequestUserListToPageCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter(ROWS_PER_PAGE));
        Optional<String> pagenumber = Optional.ofNullable(request.getParameter(Constants.PAGE_NUMBER));

        int rowsPerPage = recordsPerPage.map(Integer::parseInt)
                .orElse((Integer) request.getAttribute(ROWS_PER_PAGE));

        int page = pagenumber.map(Integer::parseInt)
                .orElse((Integer) request.getSession().getAttribute(Constants.PAGE_NUMBER));

//        LOGGER.info("rowsPerPage: {} pagenumber: {}", rowsPerPage, page);
        Account user = ((LoggedAccount) request.getSession().getAttribute(LOGGED_ACCOUNT)).getAccount();
        List<AccountActivityAndRequest> resultList;
        try {
            resultList = new EntityListService<>(AccountActivityAndRequest.class)
                    .getByStoredProc(Constants.CALL_GET_USER_ACTIVITIES_AND_REQUEST,
                            Arrays.asList(user.getId(), rowsPerPage, rowsPerPage * (page - 1)));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString(EMPTY_RESULT));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }

        request.setAttribute(Constants.COMMAND_ADMIN_ACTIVITY_LIST, resultList);
        request.getSession().setAttribute(Constants.PAGE_NUMBER, page);
        request.setAttribute(ROWS_PER_PAGE, rowsPerPage);

        LOGGER.info("OUT RequestUserListToPageCommand");
        return USER_REQUEST_LIST_JSP;
    }
}
