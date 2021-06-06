package ua.traning.rd.java.finalproject.servlet.controller.command.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;

import ua.traning.rd.java.finalproject.core.model.AdminRequestList;

import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class RequestListToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestListToPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountListToPageCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter("rowsPerPage"));
        Optional<String> pagenumber = Optional.ofNullable(request.getParameter("pagenumber"));

        int rowsPerPage = recordsPerPage.map(Integer::parseInt)
                .orElse((Integer) request.getAttribute("rowsPerPage"));

        int page = pagenumber.map(Integer::parseInt)
                .orElse((Integer) request.getSession().getAttribute("pagenumber"));

        LOGGER.info("rowsPerPage: {} pagenumber: {}", rowsPerPage, page);

        List<AdminRequestList> requestLists;
        try {
            requestLists = new EntityListService<>(AdminRequestList.class)
                    .getInRangeByRowNumber(rowsPerPage, rowsPerPage * (page - 1),
                            Constants.SQL_ADMIN_REQUEST + " LIMIT ? OFFSET ?");
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.request.data.empty"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }
        request.setAttribute("resultList", requestLists);
        request.getSession().setAttribute("pagenumber", page);
        request.setAttribute("rowsPerPage", rowsPerPage);

        LOGGER.info("rowsPerPage: {} page: {}", rowsPerPage, page);
        LOGGER.info("OUT AccountListToPageCommand");
        return "/WEB-INF/admin/requestlist.jsp";
    }
}
