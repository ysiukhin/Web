package ua.traning.rd.java.finalproject.servlet.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.traning.rd.java.finalproject.Constants.LANGUAGE;
import static ua.traning.rd.java.finalproject.Constants.LOCALE_ENGLISH;

//@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {

    protected final static Logger LOGGER = LogManager.getLogger("SessionLocaleFilter");

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
//        logger.info("BEFORE: -->  Request.getLocale(): {}", req.getLocale().toString());
//        logger.info("BEFORE: -->  sessionId: {}", req.getSession().getId());
//        logger.info("BEFORE: -->  Request.sessionLocale {}", req.getParameter("sessionLocale"));
//        logger.info("BEFORE: -->  session.lang {}", req.getSession().getAttribute("lang"));


        if (req.getSession().getAttribute(LANGUAGE) == null) {
            req.getSession().setAttribute(LANGUAGE, LOCALE_ENGLISH);
        }
        if (req.getParameter("sessionLocale") != null) {
            req.getSession().setAttribute(LANGUAGE, req.getParameter("sessionLocale"));
        }
//        else {
//            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
//        }
//        logger.info("AFTER: -->  sessionId: {}", req.getSession().getId());
//        logger.info("AFTER: -->  Request.sessionLocale {}", req.getParameter("sessionLocale"));
//        logger.info("AFTER: -->  session.lang {}", req.getSession().getAttribute("lang"));

        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}