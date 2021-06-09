package ua.traning.rd.java.finalproject.servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;

import static ua.traning.rd.java.finalproject.Constants.ALL_LOGGED_ACCOUNTS;

@WebListener
public class ApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Application started.");
        sce.getServletContext().setAttribute(ALL_LOGGED_ACCOUNTS, new HashMap<String, LoggedAccount>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Application ended.");
    }
}
