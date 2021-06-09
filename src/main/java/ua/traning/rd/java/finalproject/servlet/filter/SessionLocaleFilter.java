package ua.traning.rd.java.finalproject.servlet.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.traning.rd.java.finalproject.Constants.*;

//@WebFilter(filterName = "SessionLocaleFilter", urlPatterns = {"/*"})
public class SessionLocaleFilter implements Filter {

    protected final static Logger LOGGER = LogManager.getLogger("SessionLocaleFilter");

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute(LANGUAGE) == null) {
            req.getSession().setAttribute(LANGUAGE, LOCALE_ENGLISH);
        }
        if (req.getParameter(SESSION_LOCALE) != null) {
            req.getSession().setAttribute(LANGUAGE, req.getParameter(SESSION_LOCALE));
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}