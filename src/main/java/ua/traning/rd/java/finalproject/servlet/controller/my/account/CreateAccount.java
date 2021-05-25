package ua.traning.rd.java.finalproject.servlet.controller.my.account;

import ua.traning.rd.java.finalproject.servlet.controller.my.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/createAccount")
public class CreateAccount extends AbstractController {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToPage("CREATE ACCOUNT", "account.jsp", req, resp);
    }
}

