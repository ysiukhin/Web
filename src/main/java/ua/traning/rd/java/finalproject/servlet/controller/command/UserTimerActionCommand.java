package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.ActivityActionCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.*;

public class UserTimerActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN UserTimerActionCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        ResourceBundle messages = ResourceBundle.getBundle(MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        Optional<String> record = Optional.ofNullable(request.getParameter(RECORD));

        try {
            if (request.getParameter(RECORD).equals(ZERO)) {
                Record newRecord = new Record();
                newRecord.setStart(Timestamp.valueOf(LocalDateTime.now()));
                newRecord.setAccountActivityId(Integer.parseInt(request.getParameter(ACCOUNT_ACTIVITY_ID_VALUE)));
                new EntityListService<>(Record.class).insertEntity(newRecord);
                request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
                request.getSession().setAttribute(LAST_ACTION_MESSAGE_FULL,
                        messages.getString(TIMER_STARTED_MESSAGE));
            } else {
                new EntityListService<>(Record.class)
                        .updateEntity(Constants.STOP_TIMER_QUERY, Integer.parseInt(record.get()));
                request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
                request.getSession().setAttribute(LAST_ACTION_MESSAGE_FULL,
                        messages.getString(TIMER_STOPPED_MESSAGE));
            }
            LOGGER.info("OUT UserTimerActionCommand");
            ///TODO
            return REDIRECT + ":" + COMMAND_USER_TIMER + "?" + PAGE + "=" + COMMAND_USER_TIMER;
//            return REDIRECT+":/userTimer?page=userTimer";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
    }
}
