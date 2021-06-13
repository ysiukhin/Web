package ua.traning.rd.java.finalproject.servlet.controller.command.list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Activity;

import ua.traning.rd.java.finalproject.core.service.EntityListServiceImpl;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.RequestActionCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.*;

public class UserRequestListCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN UserRequestListCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        int rowsPerPage = DEFAULT_ROWS_PER_PAGE;
        int totalRecords;

        EntityListServiceImpl<Activity> activityService = new EntityListServiceImpl<>(Activity.class, Servlet.dataSource);
        try {
            totalRecords = activityService.totalEntityQuantity();
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
                pagesLinks.add(String.format("%s?%s=%d&%s=%d", COMMAND_USER_TO_PAGE_REQUEST, PAGE_NUMBER, i + 1, ROWS_PER_PAGE, rowsPerPage));
            }
        }

        if (Objects.isNull(request.getSession().getAttribute(PAGE_NUMBER)) ||
                Objects.isNull(request.getParameter(PAGE)) ||
                !(request.getParameter(PAGE).equals(request.getRequestURI()
                        .substring(request.getRequestURI().lastIndexOf('/') + 1)))) {
            request.getSession().setAttribute(PAGE_NUMBER, 1);
        }
        request.getSession().setAttribute(PAGINATION, pagesLinks);
        request.setAttribute(ROWS_PER_PAGE, rowsPerPage);
        LOGGER.info("OUT UserRequestListCommand");
        return COMMAND_USER_TO_PAGE_REQUEST;
    }
}
