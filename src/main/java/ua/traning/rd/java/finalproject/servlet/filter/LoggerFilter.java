package ua.traning.rd.java.finalproject.servlet.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


public class LoggerFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        logger.info("IN LoggerFilter URI: {}", req.getRequestURI());
        if (filterConfig == null) {
            return;
        }
        ServletContext ctx = filterConfig.getServletContext();
        ctx.log("" + LocalDateTime.now() + " - resourse: " + req.getRequestURL() + " is requested by: " + req.getRemoteHost());
        logger.trace("{} - resourse: {} is requested by: {}", LocalDateTime.now(), req.getRequestURL(), req.getRemoteHost());
        chain.doFilter(req, resp);
        logger.info("OUT LoggerFilter URI: {}", req.getRequestURI());
    }
}
