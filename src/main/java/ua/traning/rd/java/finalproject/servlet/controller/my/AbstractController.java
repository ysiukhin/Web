package ua.traning.rd.java.finalproject.servlet.controller.my;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractController extends HttpServlet {
    protected final Logger logger = LogManager.getLogger(getClass());

    public final void forwardToPage(String title, String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("currentPage", "page/" + jspPage);
        req.setAttribute("title", title);
//        logger.info("req.getLocale(): {} req.getSession.getAttribute(): {}",req.getLocale(), req.getSession().getAttribute("lang"));
//        req.getRequestDispatcher("/WEB-INF/JSP/"+req.getAttribute("currentPage")).forward(req, resp);
        req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
    }

    // In case Ajax will be used
    public final void forwardToFragment(String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/JSP/" + jspPage).forward(req, resp);
    }
}
