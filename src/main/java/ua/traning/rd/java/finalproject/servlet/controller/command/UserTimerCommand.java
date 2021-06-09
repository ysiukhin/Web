package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.RequestActionCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.*;

public class UserTimerCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN UserTimerCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        int rowsPerPage = Constants.DEFAULT_ROWS_PER_PAGE;
        int totalRecords = 0;

        Account user = ((LoggedAccount) request.getSession().getAttribute(LOGGED_ACCOUNT)).getAccount();
        List<AccountSignedActivities> resultList;
        try {
            resultList = new EntityListService<>(AccountSignedActivities.class)
                    .getByStoredProc(Constants.CALL_GET_USER_ACTIVITIES_AND_RECORDS,
                            Arrays.asList(user.getId(), -1, 0));
            totalRecords = resultList.size();
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString(EMPTY_RESULT));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }

        int pageCount = (int) Math.ceil((double) totalRecords / rowsPerPage);
        List<String> pagesLinks = new ArrayList<>();
        if (pageCount > 1) {
            for (int i = 0; i < Math.ceil((double) totalRecords / rowsPerPage); i++) {
                pagesLinks.add(String.format("%s?%s=%d&%s=%d", COMMAND_USER_TO_PAGE_TIMER, PAGE_NUMBER, i + 1, ROWS_PER_PAGE, rowsPerPage));
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
        LOGGER.info("OUT UserTimerCommand");
        return COMMAND_USER_TO_PAGE_TIMER;
    }
}
