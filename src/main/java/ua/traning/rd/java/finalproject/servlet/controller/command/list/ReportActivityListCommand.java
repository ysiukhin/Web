package ua.traning.rd.java.finalproject.servlet.controller.command.list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListServiceImpl;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.*;

public class ReportActivityListCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ReportActivityListCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ReportActivityListCommand");


        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter(ROWS_PER_PAGE));
        int rowsPerPage = recordsPerPage.map(Integer::parseInt).orElse(Constants.DEFAULT_ROWS_PER_PAGE);

        request.getSession().setAttribute(PAGE_NUMBER,
                Optional.ofNullable(request.getParameter(PAGE_NUMBER))
                        .map(Integer::parseInt).orElse(1));
        int page = (Integer) request.getSession().getAttribute(PAGE_NUMBER);

        List<ActivityReport> resultList;
        int totalRecords;
        try {
            totalRecords = new EntityListServiceImpl<>(Activity.class, Servlet.dataSource).totalEntityQuantity();
            resultList = new EntityListServiceImpl<>(ActivityReport.class, Servlet.dataSource)
                    .getInRangeByRowNumber(rowsPerPage, rowsPerPage * (page - 1),
                            Constants.SQL_ADMIN_REPORT_ACTIVITY + SQL_LIMIT_OFFSET_BOUNDS);
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
                pagesLinks.add(String.format("%s?%s=%d&%s=%d", COMMAND_ADMIN_REPORT_REQUEST_LIST, PAGE_NUMBER, i + 1, ROWS_PER_PAGE, rowsPerPage));
            }
        }
        request.getSession().setAttribute(PAGINATION, pagesLinks);
        request.setAttribute(ROWS_PER_PAGE, rowsPerPage);

        request.setAttribute(REPORT_ACTIVITY_LIST, resultList);

        LOGGER.info("OUT ReportActivityListCommand");
        return ADMIN_ACTIVITY_REPORT_LIST_JSP;
    }
}
