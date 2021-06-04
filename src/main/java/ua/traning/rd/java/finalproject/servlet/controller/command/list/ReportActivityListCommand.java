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
import java.util.*;
import java.util.stream.Collectors;

public class ReportActivityListCommand implements Command {
    public final static Logger LOGGER = LogManager.getLogger(ReportActivityListCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.info("IN ReportActivityListCommand");

        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
                new Locale(String.valueOf(request.getSession().getAttribute("lang"))));

        List<ActivityReport> activityReportList = new ArrayList<>();
        try {
            List<Kind> kindList = new EntityListService<>(Kind.class).getAllEntities();
            int rowNum = 0;
            for (Kind kind : kindList) {
                for (Activity activity : kind.getActivities()) {
                    activityReportList.add(new ActivityReportBuilder()
                            .addId(rowNum++)
                            .addKindEn(kind.getKindEn())
                            .addKindRu(kind.getKindRu())
                            .addActivityEn(activity.getActivityEn())
                            .addActivityRu(activity.getActivityRu())
                            .addAccountCount(activity.getActivities().size())
                            .addRequestCount(activity.getRequests().size())
                            .build()
                    );
                }
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

        int totalRecords = activityReportList.size();
        int pageCount = (int) Math.ceil((double) totalRecords / rowsPerPage);
        List<String> pagesLinks = new ArrayList<>();
        if (pageCount > 1) {
            for (int i = 0; i < Math.ceil((double) totalRecords / rowsPerPage); i++) {
                pagesLinks.add(String.format("/reportActivityList?pagenumber=%d&rowsPerPage=%d", i + 1, rowsPerPage));
            }
        }
        request.getSession().setAttribute("pagenumber",
                Optional.ofNullable(request.getParameter("pagenumber"))
                        .map(Integer::parseInt).orElse(1));
        int pageNum = (Integer) request.getSession().getAttribute("pagenumber");

        request.getSession().setAttribute("pages", pagesLinks);
        request.setAttribute("rowsPerPage", rowsPerPage);

        List<?> list = activityReportList.stream()
                .skip((long) (pageNum - 1) * rowsPerPage).limit(rowsPerPage).collect(Collectors.toList());
        request.setAttribute("activityReportList", list);


        LOGGER.info("OUT ReportActivityListCommand");
        return "/WEB-INF/admin/reportactivitylist.jsp";
    }
}
