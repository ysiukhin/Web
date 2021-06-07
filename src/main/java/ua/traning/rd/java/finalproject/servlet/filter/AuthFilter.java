package ua.traning.rd.java.finalproject.servlet.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;
import ua.traning.rd.java.finalproject.core.service.LoginService;
import ua.traning.rd.java.finalproject.servlet.controller.command.CommandUtility;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Objects.nonNull;

//@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter extends AbstractFilter {
    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("AuthFilter");
//
//        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
//                new Locale(String.valueOf(req.getSession().getAttribute("lang"))));
//
//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//        LoggedAccount currentAccount = ((LoggedAccount) req.getSession().getAttribute("account"));
//        if (currentAccount != null) {
//            req.getRequestDispatcher(req.getRequestURI()).forward(req, resp); // if user logged
//
//        } else if (nonNull(email) && nonNull(password)) {
//            if (CommandUtility.checkUserIsLogged(req, email)) { // check if user already logged from other place
//                req.getSession().setAttribute("actionStatus", false);
//                req.getSession().setAttribute("isMessage", false);
//                req.getSession().setAttribute("actionMessage", errorMessages.getString("message.user.already.logged"));
//                moveToMenu(req, resp, LoggedAccount.ROLE.UNKNOWN);
//            } else {
//                Account account;
//                try {
//                    account = new LoginService().checkAccount(email, password);
//                    //account has been authenticated and now authorize account
//                    if (!account.getStatus()) {
//                        CommandUtility.setUserRole(req, LoggedAccount.ROLE.ADMIN, account);
//                        moveToMenu(req, resp, LoggedAccount.ROLE.ADMIN);
//                    } else {
//                        CommandUtility.setUserRole(req, LoggedAccount.ROLE.USER, account);
//                        moveToMenu(req, resp, LoggedAccount.ROLE.USER);
//                    }
//                } catch (Exception e) {
//                    LOGGER.error(e.getMessage(), e);
//                    req.getSession().setAttribute("actionStatus", false);
//                    req.getSession().setAttribute("isMessage", false);
//                    req.getSession().setAttribute("actionMessage", errorMessages.getString("message.authorization.failed"));
//                    moveToMenu(req, resp, LoggedAccount.ROLE.UNKNOWN);
//                    throw new ApplicationException(errorMessages.getString("message.application.failed"));
//                }
//            }
////        } else {
////            moveToMenu(req, resp, LoggedAccount.ROLE.UNKNOWN);
////        }
//            logger.info("AuthFilter: session id: {}\nURI:   {}\naccounts:   {}", req.getSession().getId(), req.getRequestURI(),
//                    req.getSession().getAttribute("account"));
        chain.doFilter(req, resp);
//        req.getRequestDispatcher(req.getRequestURI()).forward(req, resp);
//        }
    }

    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse resp,
                            final LoggedAccount.ROLE role)
            throws ServletException, IOException {

        if (role.equals(LoggedAccount.ROLE.ADMIN)) {
            logger.info("AuthFilter: redirect -> {}/login.jsp", req.getContextPath() + "/login.jsp");
            resp.sendRedirect("redirect:/adminsection");
//            req.getRequestDispatcher("/WEB-INF/view/admin_menu.jsp").forward(req, resp);

        } else if (role.equals(LoggedAccount.ROLE.USER)) {
            resp.sendRedirect("redirect:/usersection");
        } else {
            logger.info("AuthFilter: redirect -> {}/WEB-INF/login.jsp/login.jsp", req.getContextPath());
            if (req.getRequestURI().contains("logout")) {
                resp.sendRedirect("redirect:/logout");
            } else {
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        }
    }
}


