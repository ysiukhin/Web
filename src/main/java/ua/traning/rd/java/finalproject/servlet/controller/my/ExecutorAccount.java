package ua.traning.rd.java.finalproject.servlet.controller.my;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/executor")
public class ExecutorAccount extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToPage("MANAGER ACCOUNT", "executor_account.jsp", req, resp);
    }
}
