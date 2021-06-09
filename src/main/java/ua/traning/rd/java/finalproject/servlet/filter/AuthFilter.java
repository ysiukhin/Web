package ua.traning.rd.java.finalproject.servlet.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.Constants;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.LoggedAccount;
import ua.traning.rd.java.finalproject.core.service.LoginService;
import ua.traning.rd.java.finalproject.servlet.controller.command.CommandUtility;
import ua.traning.rd.java.finalproject.servlet.exception.ApplicationException;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.Objects.nonNull;
import static ua.traning.rd.java.finalproject.Constants.LOGGED_ACCOUNT;

//@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter extends AbstractFilter {
//    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        logger.info("AuthFilter URI: {}", req.getRequestURI());
//        ResourceBundle errorMessages = ResourceBundle.getBundle("error_messages",
//                new Locale(String.valueOf(req.getSession().getAttribute("lang"))));

//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//        String uri = req.getRequestURI();
//        String session = Optional.ofNullable(req.getSession(false)).map(Present(HttpSession::getId);

//        Optional<HttpSession> session = Optional.ofNullable(req.getSession(false));
//        String sessionId = session.map(s -> {
//                    LOGGER.info("session: {}", s.getId());
//                    return s.getId();
//                }
//        ).orElseGet(() -> { LOGGER.info("session empty"); return ""; } );

        String[] uri = req.getRequestURI().split("/");
        logger.info("URI: {}", Arrays.toString(uri));
        LoggedAccount currentAccount = ((LoggedAccount) req.getSession().getAttribute(LOGGED_ACCOUNT));
        if (nonNull(currentAccount)) {
            logger.info("Loged account role: {} requested section: {}", currentAccount.getRole(),
                    uri[Constants.ACCESS_SECTION]);
            if (currentAccount.getRole()
                    .equals(LoggedAccount.ROLE
                            .valueOf(uri[Constants.ACCESS_SECTION].toUpperCase()))) {
                logger.info("currentAccount access granted");
//                        uri[Constants.ACCESS_SECTION];
            } else {
                logger.info("HttpServletResponse: error: {}", HttpServletResponse.SC_UNAUTHORIZED);
                logger.warn("currentAccount access denied");
//                HttpServletResponse.SC_UNAUTHORIZED
//                uri[Constants.ACCESS_SECTION]);
            }

        }

//        if(access(account, uri[Constants.ACCESS_SECTION])) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.sendRedirect(ACCESS_FAILED_JSP);

//        LoggedAccount currentAccount = ((LoggedAccount) req.getSession().getAttribute("account"));
//        LOGGER.info("Request URI -> {}\n\t---> Session -> {}\n\t---> currentAccount -> {}"
//                , req.getRequestURI(), sessionId, currentAccount);
//
//        String requstedArea =

//
//        if (nonNull(currentAccount)) {
//            LOGGER.info("Account URI -> {}", currentAccount.getAccount().toString());
//            moveToMenu(req, resp, currentAccount.getRole());
////            req.getRequestDispatcher(req.getRequestURI()).forward(req, resp); // if user logged
//        }

//        if ( nonNull(email) && nonNull(password)) {  // если запрос логина
//            LOGGER.info("nonNull(email): {} && nonNull(password): {}", email, password);
//            Account account;
//            try { // попытка логина
//                LOGGER.info("Try login");
//                account = new LoginService().checkAccount(email, password);
//                //account has been authenticated and now authorize account
//                LOGGER.info("Successfully");
//                if (!account.getStatus()) {
//                    CommandUtility.setUserRole(req, LoggedAccount.ROLE.ADMIN, account);
//                    moveToMenu(req, resp, LoggedAccount.ROLE.ADMIN);
//                } else {
//                    CommandUtility.setUserRole(req, LoggedAccount.ROLE.USER, account);
//                    moveToMenu(req, resp, LoggedAccount.ROLE.USER);
//                }
//            } catch (Exception e) {
//                LOGGER.error(e.getMessage(), e);
//                req.getSession().setAttribute("actionStatus", false);
//                req.getSession().setAttribute("isMessage", true);
//                req.getSession().setAttribute("actionMessage", errorMessages.getString("message.authorization.failed"));
//                moveToMenu(req, resp, LoggedAccount.ROLE.UNKNOWN);
//                throw new ApplicationException(errorMessages.getString("message.application.failed"));
//            }
//
//        }

////            if (CommandUtility.checkUserIsLogged(req, email)) { // check if user already logged from other place
////                req.getSession().setAttribute("actionStatus", false);
////                req.getSession().setAttribute("isMessage", false);
////                req.getSession().setAttribute("actionMessage", errorMessages.getString("message.user.already.logged"));
////                moveToMenu(req, resp, LoggedAccount.ROLE.UNKNOWN);
////            } else {
//
//                Account account;
//                try { // попытка логина
//                    account = new LoginService().checkAccount(email, password);
//                    //account has been authenticated and now authorize account
//
//                    if (!account.getStatus()) {
//                        CommandUtility.setUserRole(req, LoggedAccount.ROLE.ADMIN, account);
//                        moveToMenu(req, resp, LoggedAccount.ROLE.ADMIN);
//                    } else {
//                        CommandUtility.setUserRole(req, LoggedAccount.ROLE.USER, account);
//                        moveToMenu(req, resp, LoggedAccount.ROLE.USER);
//                    }
//
//                } catch (Exception e) {
//                    LOGGER.error(e.getMessage(), e);
//                    req.getSession().setAttribute("actionStatus", false);
//                    req.getSession().setAttribute("isMessage", false);
//                    req.getSession().setAttribute("actionMessage", errorMessages.getString("message.authorization.failed"));
//                    moveToMenu(req, resp, LoggedAccount.ROLE.UNKNOWN);
//                    throw new ApplicationException(errorMessages.getString("message.application.failed"));
//                }
//
//        } else {
//            moveToMenu(req, resp, LoggedAccount.ROLE.UNKNOWN);
//
//            logger.info("AuthFilter: session id: {}\nURI:   {}\naccounts:   {}", req.getSession().getId(), req.getRequestURI(),
//                    req.getSession().getAttribute("account"));

//            req.getRequestDispatcher(req.getRequestURI()).forward(req, resp);

        chain.doFilter(req, resp);
        logger.info("OUT AuthFilter URI: {}", req.getRequestURI());
    }


//    private void moveToMenu(final HttpServletRequest req,
//                            final HttpServletResponse resp,
//                            final LoggedAccount.ROLE role)
//            throws ServletException, IOException {
//
//        if (role.equals(LoggedAccount.ROLE.ADMIN)) {
//            LOGGER.info("ADMIN role: try to go to -> {}", req.getContextPath());
////            resp.sendRedirect("redirect:/adminsection");
////            req.getRequestDispatcher("/WEB-INF/view/admin_menu.jsp").forward(req, resp);
//
//        } else if (role.equals(LoggedAccount.ROLE.USER)) {
//            LOGGER.info("ROLE role: USER can go to usersection: ");
////            resp.sendRedirect("redirect:/usersection");
//        } else {
//            LOGGER.info("UNKNOWN ROLE  -> need to redirect login page: current path: {}", req.getContextPath());
////            if (req.getRequestURI().contains("logout")) {
////                resp.sendRedirect("redirect:/logout");
////            } else {
////                req.getRequestDispatcher("login.jsp").forward(req, resp);
//        }
//    }

}


