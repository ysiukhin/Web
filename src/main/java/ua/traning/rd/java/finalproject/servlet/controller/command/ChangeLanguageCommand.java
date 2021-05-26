package ua.traning.rd.java.finalproject.servlet.controller.command;

import ua.traning.rd.java.finalproject.servlet.LoggedAccount;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = request.getParameter("page");
        String role = ((LoggedAccount) request.getSession().getAttribute("account")).getRole().name().toLowerCase();
//        return /*redirect:*/"/WEB-INF/admin/adminbasis.jsp";
        return String.format("/WEB-INF/%s/%s", role, page);
    }
}
