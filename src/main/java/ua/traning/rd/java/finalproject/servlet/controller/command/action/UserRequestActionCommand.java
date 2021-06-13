package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.*;

import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.core.service.EntityListServiceImpl;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.*;

public class UserRequestActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    private final EntityListService<Request> entityListService;

    public UserRequestActionCommand(EntityListService<Request> entityListService) {
        this.entityListService = entityListService;
    }

    public UserRequestActionCommand(DataSource dataSource) {
        this.entityListService = new EntityListServiceImpl<>(Request.class, dataSource);
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN UserRequestActionCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        ResourceBundle messages = ResourceBundle.getBundle(MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        Account user = ((LoggedAccount) request.getSession().getAttribute(LOGGED_ACCOUNT)).getAccount();
        try {
            Request newRequest = new RequestBuilder()
                    .addAccountId(user.getId())
                    .addActivityId(Integer.parseInt(request.getParameter(ACTIVITY_ID)))
                    .addRequest(request.getParameter(ACCOUNT_ACTIVITY_ID_VALUE).isEmpty())
                    .build();
            StringJoiner mes = new StringJoiner(" ");
            request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);

            if (entityListService.insertEntity(newRequest) > 0) {
                request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                mes.add(messages.getString(USER_REQUEST_SUCCESS));
            } else {
                request.getSession().setAttribute(LAST_ACTION_STATUS, false);
                mes.add(messages.getString(USER_REQUEST_FAILED));
            }
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());
            LOGGER.info("OUT UserRequestActionCommand");
            return REDIRECT + ":" + COMMAND_USER_REQUEST_LIST + "?" + PAGE + "=" + COMMAND_USER_REQUEST_LIST;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
    }
}
