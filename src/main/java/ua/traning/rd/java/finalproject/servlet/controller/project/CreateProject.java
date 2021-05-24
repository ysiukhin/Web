package ua.traning.rd.java.finalproject.servlet.controller.project;

import ua.traning.rd.java.finalproject.servlet.controller.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createProject")
public class CreateProject extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToPage("CREATE PROJECT", "project.jsp", req, resp);
    }
}

