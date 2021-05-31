package ua.traning.rd.java.finalproject.servlet.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.Request;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.core.service.ExceptionService;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RequestListCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestListCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN RequestListCommand");
        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        int rowsPerPage = 10;

        int totalRecords = 0;

        EntityListService<Request> requestService = new EntityListService<>(Request.class);

        try {
            totalRecords = requestService.totalQuantity();
        } catch (ExceptionService e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.request.data.empty"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }
        int pageCount = (int) Math.ceil((double) totalRecords / rowsPerPage);
        List<String> pagesLinks = new ArrayList<>();
        if (pageCount > 1) {
            for (int i = 0; i < Math.ceil((double) totalRecords / rowsPerPage); i++) {
                pagesLinks.add(String.format("/topagerequest?pagenumber=%d&rowsPerPage=%d", i + 1, rowsPerPage));
            }
        }
        request.setAttribute("pagenumber", 1);
        request.getSession().setAttribute("pages", pagesLinks);
        request.setAttribute("rowsPerPage", rowsPerPage);

        LOGGER.info("OUT RequestListCommand");
        return "/topagerequest";
    }
}
