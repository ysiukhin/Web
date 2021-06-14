package ua.traning.rd.java.finalproject.servlet.controller.command.list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountReport;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.core.service.EntityListServiceImpl;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.*;

public class ReportAccountListCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ReportAccountListCommand.class);
    private final EntityListService<Account> entityListService;
    private final EntityListService<AccountReport> reportEntityListService;

    public ReportAccountListCommand(EntityListService<Account> entityListService,
                                    EntityListService<AccountReport> reportEntityListService) {
        this.entityListService = entityListService;
        this.reportEntityListService = reportEntityListService;
    }

    public ReportAccountListCommand(DataSource dataSource) {
        this.entityListService = new EntityListServiceImpl<>(Account.class, dataSource);
        this.reportEntityListService = new EntityListServiceImpl<>(AccountReport.class, dataSource);
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ReportActivityListCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle(ERROR_MESSAGES_BUNDLE,
                new Locale(String.valueOf(request.getSession().getAttribute(LANGUAGE))));

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter(ROWS_PER_PAGE));
        int rowsPerPage = recordsPerPage.map(Integer::parseInt).orElse(Constants.DEFAULT_ROWS_PER_PAGE);

        request.getSession().setAttribute(PAGE_NUMBER,
                Optional.ofNullable(request.getParameter(PAGE_NUMBER))
                        .map(Integer::parseInt).orElse(1));
        int page = (Integer) request.getSession().getAttribute(PAGE_NUMBER);

        List<AccountReport> resultList;
        int totalRecords;
        try {
            totalRecords = entityListService.totalEntityQuantity();
            resultList = reportEntityListService.getInRangeByRowNumber(rowsPerPage, rowsPerPage * (page - 1),
                    Constants.SQL_ADMIN_REPORT_ACCOUNT + SQL_LIMIT_OFFSET_BOUNDS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString(MESSAGE_APPLICATION_FAILED));
        }

        int pageCount = (int) Math.ceil((double) totalRecords / rowsPerPage);
        List<String> pagesLinks = new ArrayList<>();
        if (pageCount > 1) {
            for (int i = 0; i < Math.ceil((double) totalRecords / rowsPerPage); i++) {
                pagesLinks.add(String.format("%s?%s=%d&%s=%d", COMMAND_ADMIN_REPORT_ACCOUNT_LIST, PAGE_NUMBER, i + 1, ROWS_PER_PAGE, rowsPerPage));
            }
        }

        request.getSession().setAttribute(PAGINATION, pagesLinks);
        request.setAttribute(ROWS_PER_PAGE, rowsPerPage);

        request.setAttribute(REPORT_ACCOUNT_LIST, resultList);

        LOGGER.info("OUT ReportActivityListCommand");
        return ADMIN_ACCOUNT_REPORT_LIST_JSP;
    }
}
