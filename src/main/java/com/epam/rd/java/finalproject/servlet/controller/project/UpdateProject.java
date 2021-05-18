package com.epam.rd.java.finalproject.servlet.controller.project;

import com.epam.rd.java.finalproject.servlet.controller.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateProject")
public class UpdateProject extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToPage("UPDATE ACCOUNT", "project.jsp", req, resp);
    }
}

