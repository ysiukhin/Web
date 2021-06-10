package ua.traning.rd.java.finalproject.servlet.controller.command.page;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.traning.rd.java.finalproject.Constants;

import ua.traning.rd.java.finalproject.core.model.AdminActivityList;

import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static ua.traning.rd.java.finalproject.Constants.*;

public class ActivityListToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityListToPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ActivityListToPageCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter(ROWS_PER_PAGE));
        Optional<String> pagenumber = Optional.ofNullable(request.getParameter(Constants.PAGE_NUMBER));

        int rowsPerPage = recordsPerPage.map(Integer::parseInt)
                .orElse((Integer) request.getAttribute(ROWS_PER_PAGE));

        int page = pagenumber.map(Integer::parseInt)
                .orElse((Integer) request.getSession().getAttribute(Constants.PAGE_NUMBER));

        LOGGER.info("rowsPerPage: {} pagenumber: {}", rowsPerPage, page);

        List<AdminActivityList> activityList;
        try {
            activityList = new EntityListService<>(AdminActivityList.class)
                    .getAllEntitiesSql(SQL_ADMIN_ACTIVITY);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString(EMPTY_RESULT));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }

        List<?> kinds = activityList.stream().collect(Collectors.groupingBy(AdminActivityList::getKindId))
                .values().stream().map(list -> list.get(0)).collect(Collectors.toList());

        request.setAttribute(ACTIVITY_LIST, activityList.stream().filter(row -> row.getId() > 0)
                .skip((long) rowsPerPage * (page - 1)).limit(rowsPerPage).collect(Collectors.toList()));

        request.setAttribute(KIND_LIST, kinds);
        request.getSession().setAttribute(PAGE_NUMBER, page);
        request.setAttribute(ROWS_PER_PAGE, rowsPerPage);

        LOGGER.info("rowsPerPage: {} page: {}", rowsPerPage, page);
        LOGGER.info("OUT ActivityListToPageCommand");
        return ADMIN_ACTIVITY_LIST_JSP;
    }
}
