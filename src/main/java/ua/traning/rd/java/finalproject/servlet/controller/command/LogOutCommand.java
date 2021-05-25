package ua.traning.rd.java.finalproject.servlet.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)

        request.getSession().invalidate();
//
//        CommandUtility.setUserRole(request, LoggedAccount.ROLE.UNKNOWN, "Guest");
        return "redirect:/index.jsp";
    }
}
