package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountBuilder;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Objects.nonNull;
import static ua.traning.rd.java.finalproject.core.service.ServiceUtils.getMd5;

public class ActivityActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ActivityActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ActivityActionCommand");
//        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
//                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));
//
//        ResourceBundle messages = ResourceBundle.getBundle("messages",
//                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));
//
//
//        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
//            LOGGER.info("Servlet: --> getParameterMap: key : value --> {} : {}", entry.getKey(), entry.getValue());
//        }
//        try {
//            Optional<Account> newAccount = requestIsValid(request);
//            if (newAccount.isPresent()) {
//                String action = request.getParameter("action");
//                request.getSession().setAttribute("actionStatus", true);
//                request.getSession().setAttribute("isMessage", true);
//                StringJoiner mes = new StringJoiner(" ");
//                mes.add(messages.getString("entity.action.create"))
//                        .add(messages.getString("entity.dao.operation"));
//                if (messages.getString("entity.action.create").equalsIgnoreCase(action)) {
//                    if (new EntityListService<>(Account.class).insertEntity(newAccount.get()) == 0) {
//                        request.getSession().setAttribute("actionStatus", false);
//                        mes.add(messages.getString("entity.action.result.bad"));
//                    } else {
//                        mes.add(messages.getString("entity.action.result.ok"));
//                    }
//                    request.getSession().setAttribute("actionMessage", mes.toString());
//                } else if (messages.getString("entity.action.update").equalsIgnoreCase(action)) {
//                    newAccount.get().setId(Integer.parseInt(request.getParameter("id")));
//                    if (new EntityListService<>(Account.class).updateEntity(newAccount.get()) == 0) {
//                        request.getSession().setAttribute("actionStatus", false);
//                        mes.add(messages.getString("entity.action.result.bad"));
//                    } else {
//                        mes.add(messages.getString("entity.action.result.ok"));
//                    }
//                    request.getSession().setAttribute("actionMessage", mes.toString());
//                } else if (messages.getString("entity.action.delete").equalsIgnoreCase(action)) {
//                    if (new EntityListService<>(Account.class)
//                            .deleteEntity(Integer.parseInt(request.getParameter("id"))) == 0) {
//                        request.getSession().setAttribute("actionStatus", false);
//                        mes.add(messages.getString("entity.action.result.bad"));
//                    } else {
//                        mes.add(messages.getString("entity.action.result.ok"));
//                    }
//                    request.getSession().setAttribute("actionMessage", mes.toString());
//                }
//            } else {
//                request.getSession().setAttribute("actionStatus", false);
//                request.getSession().setAttribute("isMessage", true);
//                request.getSession().setAttribute("actionMessage",
//                        errorMessages.getString("message.account.validation.error"));
//            }
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//            throw new ApplicationException(errorMessages.getString("message.application.failed"));
//        }
//
//        request.getSession().setAttribute("actionStatus", true);
//        request.getSession().setAttribute("isMessage", true);
        LOGGER.info("OUT ActivityActionCommand");
        return "redirect:/accountList";
    }

    private Optional<Account> requestIsValid(final HttpServletRequest req) {

        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String middleName = req.getParameter("middle_name");
        String email = req.getParameter("email");
        String md5 = req.getParameter("md5");

        if (nonNull(firstName) && nonNull(lastName) && nonNull(email) && nonNull(md5)) {
            firstName = new String(firstName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            lastName = new String(lastName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            email = new String(email.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            md5 = new String(md5.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            if (nonNull(middleName)) {
                middleName = new String(middleName.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            }
        } else {
            return Optional.empty();
        }

        if (firstName.matches(Constants.EN_RU_LETTERS_ONLY_REGX_LEN_2_30)
                && lastName.matches(Constants.EN_RU_LETTERS_ONLY_REGX_LEN_2_30)
                && email.matches(Constants.EMAIL_REGX)
                && md5.matches(Constants.PASSWORD_REGX)) {

            Account newAccount = new AccountBuilder()
                    .addFirstName(firstName)
                    .addLastName(lastName)
                    .addMiddleName(middleName)
                    .addEmail(email)
                    .addMd5(getMd5(md5))
                    .addStatus(true)
                    .build();

            return Optional.of(newAccount);
        } else {
            return Optional.empty();
        }
    }
}
