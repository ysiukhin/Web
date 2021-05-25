package ua.traning.rd.java.finalproject.servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashSet;

@WebListener
public class ApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Application started.");
        sce.getServletContext().setAttribute("loggedAccounts", new HashSet<String>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Application ended.");
    }
}
