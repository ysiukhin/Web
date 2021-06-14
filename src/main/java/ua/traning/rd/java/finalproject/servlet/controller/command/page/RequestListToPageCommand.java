package ua.traning.rd.java.finalproject.servlet.controller.command.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.AdminRequestList;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.core.service.EntityListServiceImpl;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static ua.traning.rd.java.finalproject.Constants.*;

public class RequestListToPageCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(RequestListToPageCommand.class);
    private final EntityListService<AdminRequestList> entityListService;

    public RequestListToPageCommand(EntityListService<AdminRequestList> entityListService) {
        this.entityListService = entityListService;
    }

    public RequestListToPageCommand(DataSource dataSource) {
        this.entityListService = new EntityListServiceImpl<>(AdminRequestList.class, dataSource);
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN AccountListToPageCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter(ROWS_PER_PAGE));
        Optional<String> pagenumber = Optional.ofNullable(request.getParameter(Constants.PAGE_NUMBER));

        int rowsPerPage = recordsPerPage.map(Integer::parseInt)
                .orElse((Integer) request.getAttribute(ROWS_PER_PAGE));

        int page = pagenumber.map(Integer::parseInt)
                .orElse((Integer) request.getSession().getAttribute(Constants.PAGE_NUMBER));

        LOGGER.info("rowsPerPage: {} pagenumber: {}", rowsPerPage, page);

        List<AdminRequestList> requestLists;
        try {
            requestLists = entityListService.getInRangeByRowNumber(rowsPerPage, rowsPerPage * (page - 1),
                    Constants.SQL_ADMIN_REQUEST + SQL_LIMIT_OFFSET_BOUNDS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }
        request.setAttribute(RESULT_LIST, requestLists);
        request.getSession().setAttribute(Constants.PAGE_NUMBER, page);
        request.setAttribute(ROWS_PER_PAGE, rowsPerPage);

        LOGGER.info("rowsPerPage: {} page: {}", rowsPerPage, page);
        LOGGER.info("OUT AccountListToPageCommand");
        return ADMIN_REQUEST_LIST_JSP;
    }
}
