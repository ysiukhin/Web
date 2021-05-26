package ua.traning.rd.java.finalproject.servlet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.servlet.controller.command.*;

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
        commands.put("accountList", new AccountListCommand());
        commands.put("topageaccount", new AccountListToPageCommand());
        commands.put("changeLanguage", new ChangeLanguageCommand());
        ContextPath = config.getServletContext().getContextPath();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
        //response.getWriter().print("Hello from servlet");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF8");
//        LOGGER.info("Servlet: -->  getLocale(): {}", request.getLocale().toString());
//        LOGGER.info("Servlet: -->  sessionId: {}", request.getSession().getId());
//        LOGGER.info("Servlet: -->  sessionLocale {}", request.getParameter("sessionLocale"));
//        LOGGER.info("Servlet: -->  lang {}", request.getSession().getAttribute("lang"));
//        LOGGER.info("Servlet: -->  getCharacterEncoding {}", request.getCharacterEncoding());
//        LOGGER.info("Servlet: -->  getAttributeNames {}", request.getAttributeNames());
//        LOGGER.info("Servlet: -->  getContentLength {}", request.getContentLength());
//        LOGGER.info("Servlet: -->  getContentType {}", request.getContentType());
//        LOGGER.info("Servlet: -->  getParameterNames {}", request.getParameterNames());
//        LOGGER.info("Servlet: -->  getProtocol {}", request.getProtocol());
//        LOGGER.info("Servlet: -->  getScheme {}", request.getScheme());
//        LOGGER.info("Servlet: -->  getServerName {}", request.getServerName());
//        LOGGER.info("Servlet: -->  getServerPort {}", request.getServerPort());
//        LOGGER.info("Servlet: -->  getServerPort {}", request.getServerPort());
//        LOGGER.info("Servlet: -->  getRemoteAddr {}", request.getRemoteAddr());
//        LOGGER.info("Servlet: -->  getRemoteHost {}", request.getRemoteHost());
//        LOGGER.info("Servlet: -->  getRemotePort {}", request.getRemotePort());
//        LOGGER.info("Servlet: -->  getRemotePort {}", request.getRemotePort());
//        LOGGER.info("Servlet: -->  getLocalName {}", request.getLocalName());
//        LOGGER.info("Servlet: -->  getLocalAddr {}", request.getLocalAddr());
//        LOGGER.info("Servlet: -->  getLocalPort {}", request.getLocalPort());
//        LOGGER.info("Servlet: -->  getLocalPort {}", request.getLocalPort());

        LOGGER.info("Servlet: -->  getRequestURI: {}", request.getRequestURI());
        LOGGER.info("Servlet: -->  account: {}", request.getSession().getAttribute("account"));
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            LOGGER.info("Servlet: --> getParameterMap: key : value --> {} : {}", entry.getKey(), entry.getValue());
        }

        String path = request.getRequestURI();

        path = path.replaceAll(".*" + ContextPath + "/", "");

        LOGGER.info("Servlet: --> Command: --> : {}", commands.get(path));

        Command command = commands.getOrDefault(path, (r) -> "/index.jsp");

        String page = command.execute(request);

        LOGGER.info("Servlet: -->  page: {}", page);

//        response.sendRedirect(page);
//        request.getRequestDispatcher(page).forward(request,response);

        if (page.contains("redirect:")) {
            //            TODO
//            response.sendRedirect("/logout");
            response.sendRedirect(page.replace("redirect:", ContextPath));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}