package ua.traning.rd.java.finalproject.servlet.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.traning.rd.java.finalproject.Constants.ENCODING_UTF_8;

//@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        logger.info("EncodingFilter");
        req.setCharacterEncoding(ENCODING_UTF_8);
        resp.setCharacterEncoding(ENCODING_UTF_8);
        chain.doFilter(req, resp);
    }
}