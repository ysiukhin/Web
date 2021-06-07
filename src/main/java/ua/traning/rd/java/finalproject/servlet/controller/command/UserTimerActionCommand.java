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

public class UserTimerActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN UserTimerActionCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        Optional<String> record = Optional.ofNullable(request.getParameter("record"));

        try {
            if (request.getParameter("record").equals("0")) {
                Record newRecord = new Record();
                newRecord.setStart(Timestamp.valueOf(LocalDateTime.now()));
                newRecord.setAccountActivityId(Integer.parseInt(request.getParameter("account_activity_id")));
                new EntityListService<>(Record.class).insertEntity(newRecord);
                request.getSession().setAttribute("actionStatus", true);
                request.getSession().setAttribute("isMessage", true);
                request.getSession().setAttribute("actionMessage",
                        messages.getString("user.timer.started.message"));
            } else {
                new EntityListService<>(Record.class)
                        .updateEntity(Constants.STOP_TIMER_QUERY, Integer.parseInt(record.get()));
                request.getSession().setAttribute("actionStatus", true);
                request.getSession().setAttribute("isMessage", true);
                request.getSession().setAttribute("actionMessage",
                        messages.getString("user.timer.stopped.message"));
            }
            LOGGER.info("OUT UserTimerActionCommand");
            return "redirect:/userTimer?page=userTimer";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }
    }
}
