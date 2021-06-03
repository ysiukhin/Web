package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountBuilder;
import ua.traning.rd.java.finalproject.core.service.EntityListService;

import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

import static java.util.Objects.nonNull;

import static ua.traning.rd.java.finalproject.core.service.ServiceUtils.getMd5;

public class AccountActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AccountActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountActionCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));
        try {
            Optional<Account> newAccount = requestIsValid(request);
            if (newAccount.isPresent()) {
                String action = request.getParameter("action");
                request.getSession().setAttribute("actionStatus", true);
                request.getSession().setAttribute("isMessage", true);
                StringJoiner mes = new StringJoiner(" ");
                mes.add(messages.getString("entity.action.create")).add(messages.getString("entity.dao.operation"));
                if (messages.getString("entity.action.create").equalsIgnoreCase(action)) {
                    mes.add(insert(request, messages, newAccount.get()));
                } else if (messages.getString("entity.action.update").equalsIgnoreCase(action)) {
                    mes.add(update(request, messages, newAccount.get()));

                } else if (messages.getString("entity.action.delete").equalsIgnoreCase(action)) {
                    mes.add(delete(request, messages));
                }
                request.getSession().setAttribute("actionStatus", true);
                request.getSession().setAttribute("isMessage", true);

            } else {
                /// Если не прошло валидацию
                request.getSession().setAttribute("actionStatus", false);
                request.getSession().setAttribute("isMessage", true);
                request.getSession().setAttribute("actionMessage",
                        errorMessages.getString("message.validation.error"));
            }
            LOGGER.info("OUT AccountActionCommand");
            return "redirect:/accountList?page=accountList";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }
    }

    private String delete(HttpServletRequest request, ResourceBundle messages) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Account.class)
                .deleteEntity(Integer.parseInt(request.getParameter("id"))) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private String update(HttpServletRequest request, ResourceBundle messages, Account newAccount) {
        StringJoiner mes = new StringJoiner(" ");
        newAccount.setId(Integer.parseInt(request.getParameter("id")));
        if (new EntityListService<>(Account.class).updateEntity(newAccount) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private String insert(HttpServletRequest request, ResourceBundle messages, Account newAccount) {
        StringJoiner mes = new StringJoiner(" ");
        if (new EntityListService<>(Account.class).insertEntity(newAccount) == 0) {
            request.getSession().setAttribute("actionStatus", false);
            mes.add(messages.getString("entity.action.result.bad"));
        } else {
            mes.add(messages.getString("entity.action.result.ok"));
        }
        request.getSession().setAttribute("actionMessage", mes.toString());
        return mes.toString();
    }

    private Optional<Account> requestIsValid(final HttpServletRequest req) {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String middleName = req.getParameter("middle_name");
        String email = req.getParameter("email");
        String md5 = req.getParameter("md5");
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
                            .build()
            );
        } else {
            return Optional.empty();
        }
    }
}
