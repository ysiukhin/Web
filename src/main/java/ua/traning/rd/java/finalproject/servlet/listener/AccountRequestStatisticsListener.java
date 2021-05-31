package ua.traning.rd.java.finalproject.servlet.listener;

import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.servlet.util.UrlUtils;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebListener
@SuppressWarnings("unchecked")
public class AccountRequestStatisticsListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req = ((HttpServletRequest) sre.getServletRequest());
        String url = req.getRequestURI();
//		if(!UrlUtils.isStaticUrl(url) && !UrlUtils.isMediaUrl(url)) {
        List<String> actions = (List<String>) req.getSession().getAttribute(Constants.ACCOUNT_ACTIONS_HISTORY);
        if (actions == null) {
            actions = new ArrayList<>();
            req.getSession().setAttribute(Constants.ACCOUNT_ACTIONS_HISTORY, actions);
        }
        actions.add(getCurrentAction(req));
//		}
    }

    private String getCurrentAction(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder(req.getMethod()).append(" ").append(req.getRequestURI());

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
