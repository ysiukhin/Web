package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;

import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.Objects.nonNull;
import static ua.traning.rd.java.finalproject.Constants.*;

public class KindActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN KindActionCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        ResourceBundle messages = ResourceBundle.getBundle(MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));
        try {
            Optional<Kind> newKind = requestIsValid(request);
            if (newKind.isPresent()) {
                String action = request.getParameter(ACTION);
                request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
                StringJoiner mes = new StringJoiner(" ");
                mes.add(messages.getString(ENTITY_INSERT_ACTION))
                        .add(messages.getString(DAO_OPERATION));

                if (messages.getString(ENTITY_INSERT_ACTION).equalsIgnoreCase(action)) {
                    mes.add(insert(request, messages, newKind.get()));
                } else if (messages.getString(ENTITY_UPDATE_ACTION).equalsIgnoreCase(action)) {
                    mes.add(update(request, messages, newKind.get()));

                } else if (messages.getString(ENTITY_DELETE_ACTION).equalsIgnoreCase(action)) {
                    mes.add(delete(request, messages));
                }
            } else {
                request.getSession().setAttribute(LAST_ACTION_STATUS, false);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
                request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT,
                        errorMessages.getString(MESSAGE_VALIDATION_ERROR));
//                request.getSession().setAttribute("actionMessage",
//                        errorMessages.getString("message.validation.error"));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
        request.getSession().setAttribute(LAST_ACTION_STATUS, true);
        request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
        LOGGER.info("OUT KindActionCommand");
        return REDIRECT + ":" + COMMAND_ADMIN_KIND_LIST + "?" + Constants.PAGE + "=" + COMMAND_ADMIN_KIND_LIST;
    }

    private String delete(HttpServletRequest request, ResourceBundle messages) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Kind.class, Servlet.dataSource)
                .deleteEntity(Integer.parseInt(request.getParameter(ID))) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());
        return mes.toString();
    }

    private String update(HttpServletRequest request, ResourceBundle messages, Kind newKind) {
        StringJoiner mes = new StringJoiner(" ");
        newKind.setId(Integer.parseInt(request.getParameter(ID)));
        if (new EntityListService<>(Kind.class, Servlet.dataSource).updateEntity(newKind) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());
        return mes.toString();
    }

    private String insert(HttpServletRequest request, ResourceBundle messages, Kind newKind) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Kind.class, Servlet.dataSource).insertEntity(newKind) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());
        return mes.toString();
    }

    private Optional<Kind> requestIsValid(final HttpServletRequest req) {
        String kind_en = req.getParameter(KIND_ENGLISH);
        String kind_ru = req.getParameter(KIND_RUSSIAN);

        if (nonNull(kind_en) && nonNull(kind_ru)
                && kind_en.matches(Constants.EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_50)
                && kind_ru.matches(Constants.EN_RU_LETTERS_AND_SPACE_REGX_LEN_2_50)) {

            Kind kind = new Kind();
            kind.setKindEn(kind_en);
            kind.setKindRu(kind_ru);
            return Optional.of(kind);
        } else {
            return Optional.empty();
        }
    }
}