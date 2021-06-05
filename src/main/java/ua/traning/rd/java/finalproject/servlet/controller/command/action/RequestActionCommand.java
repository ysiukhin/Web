package ua.traning.rd.java.finalproject.servlet.controller.command.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


public class RequestActionCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestActionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN RequestActionCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));


        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            LOGGER.info("Servlet: --> getParameterMap: key : value --> {} : {}", entry.getKey(), entry.getValue());
        }
        try {
//            Request newRequest =
//                    new RequestBuilder()
//                            .addId(Integer.parseInt(request.getParameter("id")))
//                            .addAccountId(Integer.parseInt(request.getParameter("account_id")))
//                            .addActivityId(Integer.parseInt(request.getParameter("activity_id")))
//                            .addRequest((messages.getString("table.request.request.message.true")
//                                    .equalsIgnoreCase(request.getParameter("request"))))
//                            .addStatus(messages.getString("entity.action.approve")
//                                    .equalsIgnoreCase(request.getParameter("action")) ? 1 : 0)
//                            .build();
            StringJoiner informationMessage = new StringJoiner(" ");
            informationMessage.add(messages.getString("entity.dao.operation"));
            request.getSession().setAttribute("isMessage", true);
            if (messages.getString("request.result.message.reject")
                    .equalsIgnoreCase(request.getParameter("action"))) {
                if (new EntityListService<>(Request.class)
                        .deleteEntity(Integer.parseInt(request.getParameter("id"))) == 1) {
                    request.getSession().setAttribute("actionStatus", true);
                    informationMessage.add(messages.getString("entity.action.result.ok"));
                } else {
                    request.getSession().setAttribute("actionStatus", false);
                    informationMessage.add(messages.getString("entity.action.result.bad"));
                }
            } else {
                if (new RequestListService().processRequest(
                        messages.getString("table.request.request.message.true")
                                .equalsIgnoreCase(request.getParameter("request"))
                        , Integer.parseInt(request.getParameter("account_id"))
                        , Integer.parseInt(request.getParameter("activity_id"))
                        , Integer.parseInt(request.getParameter("id")))) {

                    request.getSession().setAttribute("actionStatus", true);
                    informationMessage.add(messages.getString("entity.action.result.ok"));
                } else {
                    request.getSession().setAttribute("actionStatus", false);
                    informationMessage.add(messages.getString("entity.action.result.bad"));
                }
            }
            informationMessage.add(String.format(messages.getString("request.result.message.test")
                    , request.getParameter("email")
                    , request.getParameter("account_last_name")
                    , request.getParameter("account_first_name")
                    , messages.getString("table.request.request.message.true")
                            .equalsIgnoreCase(request.getParameter("request")) ?
                            messages.getString("table.request.request.message.true") :
                            messages.getString("table.request.request.message.false")
                    , request.getParameter("activity")
                    , messages.getString("entity.action.approve")
                            .equalsIgnoreCase(request.getParameter("action")) ?
                            messages.getString("request.result.message.approve") :
                            messages.getString("request.result.message.reject")
            ));

////            StringJoiner mes = new StringJoiner(" ");
////            mes.add(messages.getString("entity.dao.operation"));
////            request.getSession().setAttribute("isMessage", true);
//            if (new EntityListService<>(Request.class)
//                    .deleteEntity(Integer.parseInt(request.getParameter("id"))) == 1) {
//            } else {
//                request.getSession().setAttribute("actionStatus", false);
//                informationMessage.add(messages.getString("entity.action.result.bad"));
//            }
            request.getSession().setAttribute("actionMessage", informationMessage.toString());
            LOGGER.info("OUT RequestActionCommand");
            return "redirect:/requestList?page=requestList";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }
    }
}
