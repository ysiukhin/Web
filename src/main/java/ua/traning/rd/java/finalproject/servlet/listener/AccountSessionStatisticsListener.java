package ua.traning.rd.java.finalproject.servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

@WebListener
@SuppressWarnings("unchecked")
public class AccountSessionStatisticsListener implements HttpSessionListener {

    public final static Logger LOGGER = LogManager.getLogger(AccountSessionStatisticsListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        List<String> actions = (List<String>) se.getSession().getAttribute(Constants.ACCOUNT_ACTIONS_HISTORY);
        if (actions != null) {
            logCurrentActionHistory(se.getSession().getId(), actions);
        }
    }

    private void logCurrentActionHistory(String sessionId, List<String> actions) {
        final String message = String.format("%s ->\n\t%s", sessionId, String.join("\n\t", actions));
        synchronized (this) {
            LOGGER.info(message);
        }
    }
}
