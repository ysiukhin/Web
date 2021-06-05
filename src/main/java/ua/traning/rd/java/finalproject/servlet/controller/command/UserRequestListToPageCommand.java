package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;
import ua.traning.rd.java.finalproject.servlet.controller.command.page.RequestListToPageCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class UserRequestListToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestListToPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN RequestUserListToPageCommand");

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
        Map<Integer, AccountActivity> accountActivities;
        Map<Integer, Request> accountRequests;
        Map<Integer, Kind> kinds;
        Account user = ((LoggedAccount) request.getSession().getAttribute("account")).getAccount();
        try {
            activities = new EntityListService<>(Activity.class)
                    .getAllEntities();
//                    .getInRangeByRowNumber(rowsPerPage, rowsPerPage * (page - 1));
            accountActivities = activities.stream().flatMap(activity -> activity.getActivities()
                    .stream()).filter(accountActivity -> accountActivity.getAccountId() == user.getId())
                    .collect(Collectors.toMap(AccountActivity::getActivityId, accountActivity -> accountActivity));
            accountRequests = activities.stream().flatMap(activity -> activity.getRequests()
                    .stream()).filter(accountRequest -> accountRequest.getAccountId() == user.getId())
                    .collect(Collectors.toMap(Request::getActivityId, accountRequest -> accountRequest));
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
        request.setAttribute("accountActivities", accountActivities);
        request.setAttribute("accountRequests", accountRequests);
        request.getSession().setAttribute("pagenumber", page);
        request.setAttribute("rowsPerPage", rowsPerPage);

        LOGGER.info("OUT RequestUserListToPageCommand");
        return "/WEB-INF/user/userrequestlist.jsp";
    }
}
