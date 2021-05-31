package ua.traning.rd.java.finalproject.servlet.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ErrorHandlerFilter", urlPatterns = "/*")
public class ErrorHandlerFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            logger.debug("Error filter: session id: {}\nRequestURI: {} \nloggedAccounts: {}",
                    req.getSession().getId(), req.getRequestURI(),
                    req.getServletContext().getAttribute("loggedAccounts"));
            chain.doFilter(req, resp);
        } catch (Throwable throwable) {
            String requestUrl = req.getRequestURI();
            logger.error("Request: {} processing error", requestUrl, throwable);
            if (!requestUrl.startsWith("/error")) {
                resp.sendRedirect("/error");
            } else {
                throw new ServletException(throwable);
            }
        }
    }
}
