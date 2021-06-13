package ua.traning.rd.java.finalproject.servlet.controller.command;

import javax.servlet.http.HttpServletRequest;

import static ua.traning.rd.java.finalproject.Constants.LOGIN_JSP;
import static ua.traning.rd.java.finalproject.Constants.REDIRECT;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return REDIRECT + ":" + LOGIN_JSP;
    }
}
