package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountActivityAndRequest;
import ua.traning.rd.java.finalproject.core.model.AccountSignedActivities;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.controller.command.page.RequestListToPageCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UserTimerToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestListToPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN UserTimerToPageCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter("rowsPerPage"));
        Optional<String> pagenumber = Optional.ofNullable(request.getParameter("pagenumber"));

        int rowsPerPage = recordsPerPage.map(Integer::parseInt)
                .orElse((Integer) request.getAttribute("rowsPerPage"));

        int page = pagenumber.map(Integer::parseInt)
                .orElse((Integer) request.getSession().getAttribute("pagenumber"));


        Account user = ((LoggedAccount) request.getSession().getAttribute("account")).getAccount();
        List<AccountSignedActivities> resultList;
        try {
            resultList = new EntityListService<>(AccountSignedActivities.class)
                    .getByStoredProc(Constants.CALL_GET_USER_ACTIVITIES_AND_RECORDS,
                            Arrays.asList(user.getId(), rowsPerPage, rowsPerPage * (page - 1)));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.request.data.empty"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }

        request.setAttribute("timerActivityList", resultList);
        request.getSession().setAttribute("pagenumber", page);
        request.setAttribute("rowsPerPage", rowsPerPage);

        LOGGER.info("OUT UserTimerToPageCommand");
        return "/WEB-INF/user/usertimerlist.jsp";
    }
}
