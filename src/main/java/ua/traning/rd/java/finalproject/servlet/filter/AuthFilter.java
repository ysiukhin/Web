package ua.traning.rd.java.finalproject.servlet.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/adminsection")
public class AuthFilter extends AbstractFilter {
    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("AuthFilter: session id: {}   \naccounts: {}", req.getSession().getId(),
                req.getSession().getAttribute("account"));
        if (req.getSession().getAttribute("account") == null) {
            LOGGER.info("AuthFilter: --> redirect to login.jsp from secured area {}", req.getContextPath() + "/login.jsp");
//            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
        chain.doFilter(req, resp);
    }
}
