package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.Objects.nonNull;
import static ua.traning.rd.java.finalproject.Constants.*;

public class ActivityActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ActivityActionCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        ResourceBundle messages = ResourceBundle.getBundle(MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));
        try {
            Optional<Activity> newActivity = requestIsValid(request);
            if (newActivity.isPresent()) {
                String action = request.getParameter(ACTION);
                request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
                StringJoiner mes = new StringJoiner(" ");
                mes.add(messages.getString(ENTITY_INSERT_ACTION))
                        .add(messages.getString(DAO_OPERATION));

                if (messages.getString(ENTITY_INSERT_ACTION).equalsIgnoreCase(action)) {
                    mes.add(insert(request, messages, newActivity.get()));
                } else if (messages.getString(ENTITY_UPDATE_ACTION).equalsIgnoreCase(action)) {
                    mes.add(update(request, messages, newActivity.get()));

                } else if (messages.getString(ENTITY_DELETE_ACTION).equalsIgnoreCase(action)) {
                    mes.add(delete(request, messages));
                }
            } else {
                request.getSession().setAttribute(LAST_ACTION_STATUS, false);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
                request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT,
                        errorMessages.getString(MESSAGE_VALIDATION_ERROR));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
        request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
        LOGGER.info("OUT ActivityActionCommand");
        return REDIRECT + ":" + COMMAND_ADMIN_ACTIVITY_LIST + "?" + PAGE + "=" + COMMAND_ADMIN_ACTIVITY_LIST;
    }

    private String delete(HttpServletRequest request, ResourceBundle messages) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Activity.class)
                .deleteEntity(Integer.parseInt(request.getParameter(ID))) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());
        return mes.toString();
    }

    private String update(HttpServletRequest request, ResourceBundle messages, Activity newActivity) {
        StringJoiner mes = new StringJoiner(" ");
        newActivity.setId(Integer.parseInt(request.getParameter(ID)));
        if (new EntityListService<>(Activity.class).updateEntity(newActivity) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());

        return mes.toString();
    }

    private String insert(HttpServletRequest request, ResourceBundle messages, Activity newActivity) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Activity.class).insertEntity(newActivity) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());

        return mes.toString();
    }

    private Optional<Activity> requestIsValid(final HttpServletRequest req) {
        String activity_en = req.getParameter(ACTIVITY_ENGLISH);
        String activity_ru = req.getParameter(ACTIVITY_RUSSIAN);
        String kind_id = req.getParameter("activity_kind");

        if (nonNull(activity_en) && nonNull(activity_ru)
                && activity_en.matches(Constants.EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_100)
                && activity_ru.matches(Constants.EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_100)) {
            return Optional.of(
                    new ActivityBuilder()
                            .addActivityEn(activity_en)
                            .addActivityRu(activity_ru)
                            .addKindId(Integer.parseInt(req.getParameter("activity_kind")))
//                            .addKindId(Integer.parseInt(req.getParameter(KIND_ID)))
                            .build());
        } else {
            return Optional.empty();
        }
    }
}
