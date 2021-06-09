package ua.traning.rd.java.finalproject.servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.traning.rd.java.finalproject.Constants.SPACE;

@WebListener
@SuppressWarnings("unchecked")
public class AccountRequestStatisticsListener implements ServletRequestListener {

    public final static Logger LOGGER = LogManager.getLogger(AccountRequestStatisticsListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest req = ((HttpServletRequest) sre.getServletRequest());
        HttpSession session = req.getSession(false);
        if (session != null) {
            List<String> actions = (List<String>) session.getAttribute(Constants.ACCOUNT_ACTIONS_HISTORY);
            actions.add(getCurrentAction(req));
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req = ((HttpServletRequest) sre.getServletRequest());
        List<String> actions = (List<String>) req.getSession().getAttribute(Constants.ACCOUNT_ACTIONS_HISTORY);
        if (actions == null) {
            actions = new ArrayList<>();
            req.getSession().setAttribute(Constants.ACCOUNT_ACTIONS_HISTORY, actions);
        }
    }

    private String getCurrentAction(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder(req.getMethod()).append(SPACE).append(req.getRequestURI());
        Map<String, String[]> map = req.getParameterMap();
        if (map != null) {
            boolean first = true;
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                if (first) {
                    sb.append('?');
                    first = false;
                } else {
                    sb.append('&');
                }
                for (String value : entry.getValue()) {
                    sb.append(entry.getKey()).append('=').append(value).append('&');
                }
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }
}
