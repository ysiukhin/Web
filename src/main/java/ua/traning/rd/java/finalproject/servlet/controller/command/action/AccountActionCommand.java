package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountBuilder;
import ua.traning.rd.java.finalproject.core.service.EntityListService;

import ua.traning.rd.java.finalproject.servlet.controller.Servlet;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import static java.util.Objects.nonNull;

import static ua.traning.rd.java.finalproject.Constants.*;
import static ua.traning.rd.java.finalproject.core.service.ServiceUtils.getMd5;

public class AccountActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AccountActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountActionCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));
        ResourceBundle messages = ResourceBundle.getBundle(MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));
        try {
            Optional<Account> newAccount = requestIsValid(request);
            if (newAccount.isPresent()) {
                String action = request.getParameter(ACTION);
                request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
                StringJoiner mes = new StringJoiner(SPACE);
                mes.add(messages.getString(ENTITY_INSERT_ACTION)).add(messages.getString(DAO_OPERATION));
                if (messages.getString(ENTITY_INSERT_ACTION).equalsIgnoreCase(action)) {
                    mes.add(insert(request, messages, newAccount.get()));
                } else if (messages.getString(ENTITY_UPDATE_ACTION).equalsIgnoreCase(action)) {
                    mes.add(update(request, messages, newAccount.get()));

                } else if (messages.getString(ENTITY_DELETE_ACTION).equalsIgnoreCase(action)) {
                    mes.add(delete(request, messages));
                }
                request.getSession().setAttribute(LAST_ACTION_STATUS, true);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);

            } else {
                request.getSession().setAttribute(LAST_ACTION_STATUS, false);
                request.getSession().setAttribute(IS_MESSAGE_TO_SHOW, true);
                request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT,
                        errorMessages.getString(MESSAGE_VALIDATION_ERROR));
            }
            LOGGER.info("OUT AccountActionCommand");
            return Constants.REDIRECT + ":"
                    + Constants.COMMAND_ADMIN_ACCOUNT_LIST + "?" + Constants.PAGE + "=" + Constants.COMMAND_ADMIN_ACCOUNT_LIST;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
    }

    private String delete(HttpServletRequest request, ResourceBundle messages) {
        StringJoiner mes = new StringJoiner(SPACE);
        if (new EntityListService<>(Account.class, Servlet.dataSource)
                .deleteEntity(Integer.parseInt(request.getParameter(ID))) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());
        return mes.toString();
    }

    private String update(HttpServletRequest request, ResourceBundle messages, Account newAccount) {
        StringJoiner mes = new StringJoiner(SPACE);
        newAccount.setId(Integer.parseInt(request.getParameter(ID)));
        if (new EntityListService<>(Account.class, Servlet.dataSource).updateEntity(newAccount) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());
        return mes.toString();
    }

    private String insert(HttpServletRequest request, ResourceBundle messages, Account newAccount) {
        StringJoiner mes = new StringJoiner(SPACE);
        if (new EntityListService<>(Account.class, Servlet.dataSource).insertEntity(newAccount) == 0) {
            request.getSession().setAttribute(LAST_ACTION_STATUS, false);
            mes.add(messages.getString(DAO_ACTION_RESULT_FAIL));
        } else {
            mes.add(messages.getString(DAO_ACTION_RESULT_OK));
        }
        request.getSession().setAttribute(LAST_ACTION_MESSAGE_SHORT, mes.toString());
//        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private Optional<Account> requestIsValid(final HttpServletRequest req) {
        String firstName = req.getParameter(FIRST_NAME);
        String lastName = req.getParameter(LAST_NAME);
        String middleName = req.getParameter(MIDDLE_NAME);
        String email = req.getParameter(EMAIL);
        String md5 = req.getParameter(PASSWORD_MD5);
        if (nonNull(firstName) && nonNull(lastName) && nonNull(email) && nonNull(md5)
                && firstName.matches(Constants.EN_RU_LETTERS_ONLY_REGX_LEN_2_30)
                && lastName.matches(Constants.EN_RU_LETTERS_ONLY_REGX_LEN_2_30)
                && email.matches(Constants.EMAIL_REGX)
                && md5.matches(Constants.PASSWORD_REGX)) {

            return Optional.of(
                    new AccountBuilder()
                            .addFirstName(firstName)
                            .addLastName(lastName)
                            .addMiddleName(middleName)
                            .addEmail(email)
                            .addMd5(getMd5(md5))
                            .addStatus(true)
                            .build());
        } else {
            return Optional.empty();
        }
    }
}
