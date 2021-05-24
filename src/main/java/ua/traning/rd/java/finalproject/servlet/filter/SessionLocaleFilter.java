package ua.traning.rd.java.finalproject.servlet.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {

    protected final Logger logger = LogManager.getLogger("MysqlDemo");

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

//        HttpSession session = req.getSession();
//        String sessionId = req.getSession().getId();
//        String sessionLocale = req.getSession().getAttribute("sessionLocale").toString();
//        String sessionLocale = req.getSession().getAttribute("sessionLocale").toString();

        logger.info("BEFORE: -->  Request.getLocale(): {}", req.getLocale().toString());
        logger.info("BEFORE: -->  sessionId: {}", req.getSession().getId());
        logger.info("BEFORE: -->  Request.sessionLocale {}", req.getParameter("sessionLocale"));
        logger.info("BEFORE: -->  session.lang {}", req.getSession().getAttribute("lang"));

        if (req.getSession().getAttribute("lang") == null) {
            req.getSession().setAttribute("lang", "en");
        }
        if (req.getParameter("sessionLocale") != null) {
            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
        }
//        else {
//            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
//        }
        logger.info("AFTER: -->  sessionId: {}", req.getSession().getId());
        logger.info("AFTER: -->  Request.sessionLocale {}", req.getParameter("sessionLocale"));
        logger.info("AFTER: -->  session.lang {}", req.getSession().getAttribute("lang"));

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}