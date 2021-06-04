package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.ActivityActionCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.Objects.nonNull;

public class ActivityUserActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ActivityUserActionCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));


        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            LOGGER.info("Servlet: --> getParameterMap: key : value --> {} : {}", entry.getKey(), entry.getValue());
        }
        Account user = ((LoggedAccount) request.getSession().getAttribute("account")).getAccount();
        try {
            Optional<String> account_activity_id = Optional.ofNullable(request.getParameter("account_activity_id"));
            Optional<String> activity_id = Optional.ofNullable(request.getParameter("activity_id"));
            Optional<String> request_id = Optional.ofNullable(request.getParameter("request_id"));
            Request newRequest = null;
            if (!account_activity_id.isPresent() && !request_id.isPresent()) {
                newRequest = new RequestBuilder()
                        .addAccountId(user.getId())
                        .addActivityId(Integer.parseInt(activity_id.get()))
                        .addRequest(true)
                        .build();
            }
            StringJoiner mes = new StringJoiner(" ");
            mes.add(messages.getString("entity.dao.operation"));

            if (new EntityListService<>(Request.class).updateEntity(newRequest) == 1) {
                request.getSession().setAttribute("actionStatus", true);
                request.getSession().setAttribute("isMessage", true);
                mes.add(messages.getString("entity.action.result.ok"));
            } else {
                request.getSession().setAttribute("actionStatus", false);
                request.getSession().setAttribute("isMessage", true);
                mes.add(messages.getString("entity.action.result.ok"));
            }
            request.getSession().setAttribute("actionMessage", mes.toString());
            LOGGER.info("OUT RequestActionCommand");
            return "redirect:/userRequestList?page=userRequestList";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }


//        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
//                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));
//
//        ResourceBundle messages = ResourceBundle.getBundle("messages",
//                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));
//        try {
//            Optional<Activity> newActivity = requestIsValid(request);
//            if (newActivity.isPresent()) {
//                String action = request.getParameter("action");
//                request.getSession().setAttribute("actionStatus", true);
//                request.getSession().setAttribute("isMessage", true);
//                StringJoiner mes = new StringJoiner(" ");
//                mes.add(messages.getString("entity.action.create"))
//                        .add(messages.getString("entity.dao.operation"));
//
//                if (messages.getString("entity.action.create").equalsIgnoreCase(action)) {
//                    mes.add(insert(request, messages, newActivity.get()));
//                } else if (messages.getString("entity.action.update").equalsIgnoreCase(action)) {
//                    mes.add(update(request, messages, newActivity.get()));
//
//                } else if (messages.getString("entity.action.delete").equalsIgnoreCase(action)) {
//                    mes.add(delete(request, messages));
//                }
//            } else {
//                request.getSession().setAttribute("actionStatus", false);
//                request.getSession().setAttribute("isMessage", true);
//                request.getSession().setAttribute("actionMessage",
//                        errorMessages.getString("message.validation.error"));
//            }
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//            throw new ApplicationException(errorMessages.getString("message.application.failed"));
//        }
//        request.getSession().setAttribute("actionStatus", true);
//        request.getSession().setAttribute("isMessage", true);
//        LOGGER.info("OUT ActivityUserActionCommand");
//        return "redirect:/activityList?page=activityList";
    }


    private String delete(HttpServletRequest request, ResourceBundle messages) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Activity.class)
                .deleteEntity(Integer.parseInt(request.getParameter("id"))) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private String update(HttpServletRequest request, ResourceBundle messages, Activity newActivity) {
        StringJoiner mes = new StringJoiner(" ");
        newActivity.setId(Integer.parseInt(request.getParameter("id")));
        if (new EntityListService<>(Activity.class).updateEntity(newActivity) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private String insert(HttpServletRequest request, ResourceBundle messages, Activity newActivity) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Activity.class).insertEntity(newActivity) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private Optional<Activity> requestIsValid(final HttpServletRequest req) {

        String activity_en = req.getParameter("activity_en");
        String activity_ru = req.getParameter("activity_ru");
        String activity_kind = req.getParameter("activity_kind");

        if (nonNull(activity_en) && nonNull(activity_ru)
                && activity_en.matches(Constants.EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_50)
                && activity_ru.matches(Constants.EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_50)) {
            return Optional.of(
                    new ActivityBuilder()
                            .addActivityEn(activity_en)
                            .addActivityRu(activity_ru)
                            .addKindId(new EntityListService<>(Kind.class)
                                    .getIdByColumn("kind_en",
                                            req.getParameter("activity_kind")
                                                    .substring(0, activity_kind.indexOf("|") - 1)
                                    )
                                    .get(0).getId())
                            .addStatus(true)
                            .build());
        } else {
            return Optional.empty();
        }
    }
}
