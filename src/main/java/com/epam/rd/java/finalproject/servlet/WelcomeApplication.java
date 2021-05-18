package com.epam.rd.java.finalproject.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.context.WebContext;

public class WelcomeApplication {
    private final Logger logger = LogManager.getLogger(getClass());
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setLocale(new Locale("ru", "RU"));
        response.setCharacterEncoding("UTF-8");
        WebContext ctx = new WebContext(request, response, request.getServletContext(), new Locale("ru", "RU"));

//        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        ctx.setVariable("currentDate", new Date());
        logger.debug("request.getLocale().getLanguage(): {} ", request.getLocale().getLanguage());
        logger.debug("response getLanguage(): {} and getCharacterEncoding: {}", response.getLocale().getLanguage(), response.getCharacterEncoding());
        ThymeleafAppUtil.getTemplateEngine().process("welcome", ctx, response.getWriter());
    }
}
