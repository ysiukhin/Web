package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AccountAction implements Command {
    public final static Logger LOGGER = LogManager.getLogger(AccountAction.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountListCommand");
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            LOGGER.info("Servlet: --> getParameterMap: key : value --> {} : {}", entry.getKey(), entry.getValue());
        }


        request.getSession().setAttribute("actionStatus", true);
        request.getSession().setAttribute("isMessage", true);
        LOGGER.info("OUT AccountListCommand");
        return "redirect:/accountList";
    }
}
