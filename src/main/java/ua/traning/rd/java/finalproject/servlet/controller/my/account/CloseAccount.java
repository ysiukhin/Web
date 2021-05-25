package ua.traning.rd.java.finalproject.servlet.controller.my.account;

import ua.traning.rd.java.finalproject.servlet.controller.my.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

//@WebServlet("/closeAccount")
public class CloseAccount extends AbstractController {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResourceBundle bundle =
                ResourceBundle.getBundle("messages", req.getLocale());
        String message = bundle.getString("label.close") + " " + bundle.getString("label.account");
        forwardToPage(message, "account.jsp", req, resp);
//        forwardToPage("CLOSE ACCOUNT", "account.jsp", req, resp);
    }
}

