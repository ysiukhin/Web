package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Request;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.core.service.RequestListService;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringJoiner;

import static ua.traning.rd.java.finalproject.Constants.*;


public class RequestActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN RequestActionCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        ResourceBundle messages = ResourceBundle.getBundle(MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));


        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            LOGGER.info("Servlet: --> getParameterMap: key : value --> {} : {}", entry.getKey(), entry.getValue());
        }
        try {
            StringJoiner informationMessage = new StringJoiner(SPACE);
            StringJoiner captionMessage = new StringJoiner(SPACE);
            captionMessage.add(messages.getString(DAO_OPERATION));
            request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
            if (messages.getString(REQUEST_REJECTED)
                    .equalsIgnoreCase(request.getParameter(ACTION))) {
                if (new EntityListService<>(Request.class)
                        .deleteEntity(Integer.parseInt(request.getParameter(ID))) == 1) {
                    request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                    captionMessage.add(messages.getString(DAO_ACTION_RESULT_OK));
                } else {
                    request.getSession().setAttribute(LAST_ACTION_STATUS, false);
                    captionMessage.add(messages.getString(DAO_ACTION_RESULT_FAIL));
                }
            } else {
                if (new RequestListService().processRequest(
                        messages.getString(SIGN_IN)
                                .equalsIgnoreCase(request.getParameter(REQUEST))
                        , Integer.parseInt(request.getParameter(ACCOUNT_ID))
                        , Integer.parseInt(request.getParameter(ACTIVITY_ID))
                        , Integer.parseInt(request.getParameter(ID)))) {

                    request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                    captionMessage.add(messages.getString(DAO_ACTION_RESULT_OK));
                } else {
                    request.getSession().setAttribute(LAST_ACTION_STATUS, false);
                    captionMessage.add(messages.getString(DAO_ACTION_RESULT_FAIL));
                }
            }
            informationMessage.add(String.format(messages.getString(RESULT_MESSAGE_TEXT)
                    , request.getParameter(EMAIL)
                    , request.getParameter(ACCOUNT_LAST_NAME)
                    , request.getParameter(ACCOUNT_FIRST_NAME)
                    , messages.getString(SIGN_IN)
                            .equalsIgnoreCase(request.getParameter(REQUEST)) ?
                            messages.getString(SIGN_IN) :
                            messages.getString(SIGN_OUT)
                    , request.getParameter(ACTIVITY)
                    , messages.getString(REQUEST_APPROVE_ACTION)
                            .equalsIgnoreCase(request.getParameter(ACTION)) ?
                            messages.getString(REQUEST_APPROVED) :
                            messages.getString(REQUEST_REJECTED)
            ));

            request.getSession().setAttribute(LAST_ACTION_MESSAGE_FULL, informationMessage.toString());
            request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, captionMessage.toString());
            LOGGER.info("OUT RequestActionCommand");
            return REDIRECT + ":" + COMMAND_ADMIN_REQUEST_LIST + "?" + Constants.PAGE + "=" + COMMAND_ADMIN_REQUEST_LIST;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
    }
}
