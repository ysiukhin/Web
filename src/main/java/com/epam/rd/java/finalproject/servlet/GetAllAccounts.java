package com.epam.rd.java.finalproject.servlet;

import com.epam.rd.java.finalproject.MysqlDemo;
import com.epam.rd.java.finalproject.core.dao.AccountDao;
import com.epam.rd.java.finalproject.core.model.Account;
import com.epam.rd.java.finalproject.core.model.AccountBuilder;
import com.epam.rd.java.finalproject.core.service.DbServiceAccount;
import com.epam.rd.java.finalproject.core.service.DbServiceAccountImpl;
import com.epam.rd.java.finalproject.jdbc.DbExecutorImpl;
import com.epam.rd.java.finalproject.jdbc.dao.AccountDaoJdbc;
import com.epam.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//@WebServlet("/accounts")
public class GetAllAccounts extends HttpServlet {
    private final static String index = "/WEB-INF/view/index.jsp";
    private List<Account> accounts;
    private static final Logger logger = LogManager.getLogger("GetAllAccounts");

    @Resource(name = "jdbc/timecounterdb")
    DataSource dataSource;

    @Override
    public void init() {
        try {
            SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);
            DbExecutorImpl<Account> dbExecutor = new DbExecutorImpl<>();
            AccountDao accountDao = new AccountDaoJdbc(sessionManagerJdbc, dbExecutor);


            MysqlDemo.insertTestAccounts(accountDao, 5, getServletContext().getRealPath("/"));
            DbServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);
            accounts = new CopyOnWriteArrayList<>(dbServiceAccount.getAllAccounts().get());

        } catch (IOException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher(index).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");

        if (!requestIsValid(req)) {
            doGet(req, resp);
        }

        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<Account> dbExecutor = new DbExecutorImpl<>();
        AccountDao accountDao = new AccountDaoJdbc(sessionManagerJdbc, dbExecutor);

        DbServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);

        final Account account = new AccountBuilder()
                .addFirstName(req.getParameter("firstName"))
                .addLastName(req.getParameter("lastName"))
                .addMiddleName(req.getParameter("middleName"))
                .addEmail(req.getParameter("email"))
                .addLogin(req.getParameter("login"))
                .addMd5(MysqlDemo.getMd5(req.getParameter("login")))
                .addStatus(Integer.parseInt(req.getParameter("status")) == 1)
                .addRoleId(Integer.parseInt(req.getParameter("status")))
                .build();

        int id = dbServiceAccount.saveAccount(account);
        logger.info("servlet GetAllAccounts inserts:{} new Account: {}", id, account);
        doGet(req, resp);
    }

    private boolean requestIsValid(final HttpServletRequest req) {

//        final String name = req.getParameter("name");
//        final String age = req.getParameter("age");
//        final String firstName = req.getParameter("firstName");
//        final String lastName = req.getParameter("lastName");
//        final String middleName = req.getParameter("middleName");
//        final String email = req.getParameter("email");
//        final String login = req.getParameter("login");
//        final String md5 = MysqlDemo.getMd5(req.getParameter("login"));
//        final boolean status = Integer.parseInt(req.getParameter("status")) == 1;
//        final int roleId = Integer.parseInt(req.getParameter("status"));

//        name != null && name.length() > 0
//                && age != null && age.length() > 0 &&
//                age.matches("[+]?\\d+");
//        md5 = MysqlDemo.getMd5(req.getParameter("login"));
        return true;
    }
}
