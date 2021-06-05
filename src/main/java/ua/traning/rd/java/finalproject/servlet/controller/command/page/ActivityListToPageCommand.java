package ua.traning.rd.java.finalproject.servlet.controller.command.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.traning.rd.java.finalproject.core.model.Activity;
import ua.traning.rd.java.finalproject.core.model.Kind;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class ActivityListToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityListToPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ActivityListToPageCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter("rowsPerPage"));
        Optional<String> pagenumber = Optional.ofNullable(request.getParameter("pagenumber"));

        int rowsPerPage = recordsPerPage.map(Integer::parseInt)
                .orElse((Integer) request.getAttribute("rowsPerPage"));

        int page = pagenumber.map(Integer::parseInt)
                .orElse((Integer) request.getSession().getAttribute("pagenumber"));

        LOGGER.info("rowsPerPage: {} pagenumber: {}", rowsPerPage, page);

        List<Activity> activities;
        Map<Integer, Kind> kinds;
        try {
            activities = new EntityListService<>(Activity.class)
                    .getInRangeByRowNumber(rowsPerPage, rowsPerPage * (page - 1));
            kinds = new EntityListService<>(Kind.class).getAllEntities()
                    .stream().collect(Collectors.toMap(Kind::getId, kind -> kind));

        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.request.data.empty"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }

        request.setAttribute("activities", activities);
        request.setAttribute("kinds", kinds);
        request.getSession().setAttribute("pagenumber", page);
        request.setAttribute("rowsPerPage", rowsPerPage);

        LOGGER.info("rowsPerPage: {} page: {}", rowsPerPage, page);
        LOGGER.info("OUT ActivityListToPageCommand");
        return "/WEB-INF/admin/activitylist.jsp";
    }
}
