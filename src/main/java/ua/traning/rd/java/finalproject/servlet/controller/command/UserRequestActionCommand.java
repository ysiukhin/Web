package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.*;

import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.ActivityActionCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UserRequestActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN UserRequestActionCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

//        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
//            LOGGER.info("Servlet: --> getParameterMap: key : value --> {} : {}", entry.getKey(), entry.getValue());
//        }

        Account user = ((LoggedAccount) request.getSession().getAttribute("account")).getAccount();
        try {
            Request newRequest = new RequestBuilder()
                    .addAccountId(user.getId())
                    .addActivityId(Integer.parseInt(request.getParameter("activity_id")))
                    .addRequest(request.getParameter("account_activity_id").isEmpty())
                    .build();
            StringJoiner mes = new StringJoiner(" ");
            request.getSession().setAttribute("isMessage", true);

            if (new EntityListService<>(Request.class).insertEntity(newRequest) == 1) {
                request.getSession().setAttribute("actionStatus", true);
                mes.add(messages.getString("user.request.ok"));
            } else {
                request.getSession().setAttribute("actionStatus", false);
                mes.add(messages.getString("user.request.ok"));
            }
            request.getSession().setAttribute("actionMessage", mes.toString());
            LOGGER.info("OUT UserRequestActionCommand");
            return "redirect:/userRequestList?page=userRequestList";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }
    }
}
