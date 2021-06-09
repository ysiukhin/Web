package ua.traning.rd.java.finalproject.servlet.controller.command;

import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;

import javax.servlet.http.HttpServletRequest;

import static ua.traning.rd.java.finalproject.Constants.LOGGED_ACCOUNT;


public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = request.getParameter(Constants.PAGE);
        String role = ((LoggedAccount) request.getSession().getAttribute(LOGGED_ACCOUNT)).getRole().name().toLowerCase();

//        return /*redirect:*/"/WEB-INF/admin/adminbasis.jsp";
//        return String.format("redirect:/WEB-INF/%s/%s", role, page);
//        request.setAttribute("pagenumber", request.getParameter("pagenumber"));
//        request.setAttribute("rowsPerPage", rowsPerPage);

        return String.format("/%s", page);
//        return String.format("redirect:/%s", page);
//        return String.format("redirect:/%s&%S&%s", page,request.getParameter("pagenumber"),request.getParameter("rowsPerPage"));
    }
}
