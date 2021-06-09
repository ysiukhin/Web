package ua.traning.rd.java.finalproject.servlet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.servlet.controller.command.*;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.*;
import ua.traning.rd.java.finalproject.servlet.controller.command.list.*;
import ua.traning.rd.java.finalproject.servlet.controller.command.page.*;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import static ua.traning.rd.java.finalproject.Constants.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    public final static Logger LOGGER = LogManager.getLogger(Servlet.class);

    @Resource(name = DBNAME)
    public static DataSource dataSource;
    public static String ContextPath;
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        commands.put(COMMAND_ERROR, (r) -> ERROR_JSP);

        commands.put(COMMAND_LOGOUT, new LogOutCommand());
        commands.put(COMMAND_LOGIN, new LoginCommand());

        commands.put(COMMAND_ADMIN_SECTION, new AdminSectionCommand());
        commands.put(COMMAND_USER_SECTION, new UserSectionCommand());

        commands.put(COMMAND_ADMIN_ACCOUNT_LIST, new AccountListCommand());
        commands.put(COMMAND_ADMIN_ACTIVITY_LIST, new ActivityListCommand());
        commands.put(COMMAND_ADMIN_KIND_LIST, new KindListCommand());
        commands.put(COMMAND_ADMIN_REQUEST_LIST, new RequestListCommand());
        commands.put(COMMAND_ADMIN_REPORT_REQUEST_LIST, new ReportActivityListCommand());
        commands.put(COMMAND_ADMIN_REPORT_ACCOUNT_LIST, new ReportAccountListCommand());

        commands.put(COMMAND_USER_TIMER, new UserTimerCommand());
        commands.put(COMMAND_USER_REQUEST_LIST, new UserRequestListCommand());


        commands.put(COMMAND_ADMIN_TO_PAGE_ACCOUNT, new AccountListToPageCommand());
        commands.put(COMMAND_ADMIN_TO_PAGE_ACTIVITY, new ActivityListToPageCommand());
        commands.put(COMMAND_ADMIN_TO_PAGE_KIND, new KindListToPageCommand());
        commands.put(COMMAND_ADMIN_TO_PAGE_REQUEST, new RequestListToPageCommand());
        commands.put(COMMAND_USER_TO_PAGE_REQUEST, new UserRequestListToPageCommand());
        commands.put(COMMAND_USER_TO_PAGE_TIMER, new UserTimerToPageCommand());


        commands.put(COMMAND_ADMIN_ACCOUNT_ACTION, new AccountActionCommand());
        commands.put(COMMAND_ADMIN_ACTIVITY_ACTION, new ActivityActionCommand());
        commands.put(COMMAND_ADMIN_KIND_ACTION, new KindActionCommand());
        commands.put(COMMAND_ADMIN_REQUEST_ACTION, new RequestActionCommand());
        commands.put(COMMAND_USER_REQUEST_ACTION, new UserRequestActionCommand());
        commands.put(COMMAND_USER_TIMER_ACTION, new UserTimerActionCommand());

        commands.put(COMMAND_CHANGE_LANGUAGE, new ChangeLanguageCommand());
        ContextPath = config.getServletContext().getContextPath();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
//        path = path.replaceAll(".*" + ContextPath + "/", "");

        path = path.replaceAll(".*" + ContextPath, "");

        LOGGER.info("Before command execute: path -> {}", path);
        Command command = commands.getOrDefault(path, (r) -> COMMAND_LOGOUT);

        String page = command.execute(request);

        LOGGER.info("Command to path -> {} executed result path: {}", path, page);

        if (page.contains(REDIRECT + ":")) {
//            String redirect = page.replace(REDIRECT + ":", ContextPath);
            response.sendRedirect(page.replace(REDIRECT + ":", ContextPath));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}