package ua.traning.rd.java.finalproject.servlet.controller.my;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/ceo")
public class CeoAccount extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToPage("CEO ACCOUNT", "ceo_account.jsp", req, resp);
    }
}
