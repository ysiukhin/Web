package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.ActivityActionCommand;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.Objects.nonNull;

public class KindActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN KindActionCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));
        try {
            Optional<Kind> newKind = requestIsValid(request);
            if (newKind.isPresent()) {
                String action = request.getParameter("action");
                request.getSession().setAttribute("actionStatus", true);
                request.getSession().setAttribute("isMessage", true);
                StringJoiner mes = new StringJoiner(" ");
                mes.add(messages.getString("entity.action.create"))
                        .add(messages.getString("entity.dao.operation"));

                if (messages.getString("entity.action.create").equalsIgnoreCase(action)) {
                    mes.add(insert(request, messages, newKind.get()));
                } else if (messages.getString("entity.action.update").equalsIgnoreCase(action)) {
                    mes.add(update(request, messages, newKind.get()));

                } else if (messages.getString("entity.action.delete").equalsIgnoreCase(action)) {
                    mes.add(delete(request, messages));
                }
            } else {
                request.getSession().setAttribute("actionStatus", false);
                request.getSession().setAttribute("isMessage", true);
                request.getSession().setAttribute("actionMessage",
                        errorMessages.getString("message.validation.error"));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }
        request.getSession().setAttribute("actionStatus", true);
        request.getSession().setAttribute("isMessage", true);
        LOGGER.info("OUT KindActionCommand");
        return "redirect:/kindList?page=kindList";
    }

    private String delete(HttpServletRequest request, ResourceBundle messages) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Kind.class)
                .deleteEntity(Integer.parseInt(request.getParameter("id"))) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private String update(HttpServletRequest request, ResourceBundle messages, Kind newKind) {
        StringJoiner mes = new StringJoiner(" ");
        newKind.setId(Integer.parseInt(request.getParameter("id")));
        if (new EntityListService<>(Kind.class).updateEntity(newKind) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private String insert(HttpServletRequest request, ResourceBundle messages, Kind newKind) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Kind.class).insertEntity(newKind) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private Optional<Kind> requestIsValid(final HttpServletRequest req) {
        String kind_en = req.getParameter("kind_en");
        String kind_ru = req.getParameter("kind_ru");

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
