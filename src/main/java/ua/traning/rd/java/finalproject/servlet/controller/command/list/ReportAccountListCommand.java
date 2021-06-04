package ua.traning.rd.java.finalproject.servlet.controller.command.list;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.core.service.EntityListService;
import ua.traning.rd.java.finalproject.core.service.ExceptionService;
import ua.traning.rd.java.finalproject.servlet.controller.command.Command;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;
import ua.traning.rd.java.finalproject.servlet.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ReportAccountListCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ReportAccountListCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ReportActivityListCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        List<AccountReport> accountReportList = new ArrayList<>();

        try {
            List<Account> accountList = new EntityListService<>(Account.class).getAllEntities();
            int rowNum = 0;
            for (Account account : accountList) {
                accountReportList.add(new AccountReportBuilder()
                        .addId(rowNum++)
                        .addFirstName(account.getFirstName())
                        .addLastName(account.getLastName())
                        .addEmail(account.getEmail())
                        .addActivityCount(account.getAccountActivities().size())
                        .addTotalTimeInMinutes(account.getAccountActivities().stream()
                                .flatMap(accountActivity -> accountActivity.getRecords().stream())
                                .mapToInt(record ->
                                        (int) Duration.between(record.getStart().toLocalDateTime(),
                                                record.getEnd().toLocalDateTime()).toMinutes()).sum()
                        )
                        .build()
                );
            }
        } catch (ExceptionService e) {
            LOGGER.error(e.getMessage(), e);
            throw new CommandException(errorMessages.getString("message.request.data.empty"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ApplicationException(errorMessages.getString("message.application.failed"));
        }

        Optional<String> recordsPerPage = Optional.ofNullable(request.getParameter("rowsPerPage"));
        int rowsPerPage = recordsPerPage.map(Integer::parseInt).orElse(Constants.ROWS_PER_PAGE);

        int totalRecords = accountReportList.size();
        int pageCount = (int) Math.ceil((double) totalRecords / rowsPerPage);
        List<String> pagesLinks = new ArrayList<>();
        if (pageCount > 1) {
            for (int i = 0; i < Math.ceil((double) totalRecords / rowsPerPage); i++) {
                pagesLinks.add(String.format("/reportAccountList?pagenumber=%d&rowsPerPage=%d", i + 1, rowsPerPage));
            }
        }
        request.getSession().setAttribute("pagenumber",
                Optional.ofNullable(request.getParameter("pagenumber"))
                        .map(Integer::parseInt).orElse(1));
        int pageNum = (Integer) request.getSession().getAttribute("pagenumber");

        request.getSession().setAttribute("pages", pagesLinks);
        request.setAttribute("rowsPerPage", rowsPerPage);

        List<?> list = accountReportList.stream()
                .skip((long) (pageNum - 1) * rowsPerPage).limit(rowsPerPage).collect(Collectors.toList());
        request.setAttribute("accountReportList", list);

        LOGGER.info("OUT ReportActivityListCommand");
        return "/WEB-INF/admin/reportaccountlist.jsp";
    }
}
