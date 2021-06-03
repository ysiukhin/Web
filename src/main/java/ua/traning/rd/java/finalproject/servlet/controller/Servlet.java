package ua.traning.rd.java.finalproject.servlet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.servlet.controller.command.*;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.AccountActionCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.ActivityActionCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.KindActionCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.action.RequestActionCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.list.AccountListCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.list.ActivityListCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.list.KindListCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.list.RequestListCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.page.AccountListToPageCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.page.ActivityListToPageCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.page.KindListToPageCommand;
import ua.traning.rd.java.finalproject.servlet.controller.command.page.RequestListToPageCommand;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    public final static Logger LOGGER = LogManager.getLogger(Servlet.class);

    @Resource(name = "jdbc/timecounterdb")
    public static DataSource dataSource;
    public static String ContextPath;
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        commands.put("error", (r) -> "/WEB-INF/error.jsp");
        commands.put("logout", new LogOutCommand());
        commands.put("login", new LoginCommand());
        commands.put("adminsection", new AdminSectionCommand());
        commands.put("usersection", new UserSectionCommand());

        commands.put("accountList", new AccountListCommand());
        commands.put("activityList", new ActivityListCommand());
        commands.put("kindList", new KindListCommand());
        commands.put("requestList", new RequestListCommand());

        commands.put("topageaccount", new AccountListToPageCommand());
        commands.put("topageactivity", new ActivityListToPageCommand());
        commands.put("topagekind", new KindListToPageCommand());
        commands.put("topagerequest", new RequestListToPageCommand());

        commands.put("accountAction", new AccountActionCommand());
        commands.put("activityAction", new ActivityActionCommand());
        commands.put("kindAction", new KindActionCommand());
        commands.put("requestAction", new RequestActionCommand());  // TODO

        commands.put("changeLanguage", new ChangeLanguageCommand());
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
        path = path.replaceAll(".*" + ContextPath + "/", "");
        Command command = commands.getOrDefault(path, (r) -> "/index.jsp");
        String page = command.execute(request);
        if (page.contains("redirect:")) {
            String redirect = page.replace("redirect:", ContextPath);
//            LOGGER.info("redirect {} to -> {}", page, redirect);
            response.sendRedirect(page.replace("redirect:", ContextPath));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}