package ua.traning.rd.java.finalproject.servlet;

import ua.traning.rd.java.finalproject.SampleData;
import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;

import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountBuilder;
import ua.traning.rd.java.finalproject.core.dao.DbService;

import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;

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

import static ua.traning.rd.java.finalproject.Constants.ACCOUNT_LIST;
import static ua.traning.rd.java.finalproject.Constants.EMAIL;

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
            Dao<Account> accountDao = new DaoJdbc<>(sessionManagerJdbc, Account.class);

            SampleData.init(dataSource);
            DbService<Account> dbService = new DbServiceImpl<>(accountDao);
            accounts = new CopyOnWriteArrayList<>(dbService.getAllBeans().get());

        } catch (IOException | NoSuchAlgorithmException | SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute(ACCOUNT_LIST, accounts);
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
        Dao<Account> accountDao = new DaoJdbc<>(sessionManagerJdbc, Account.class);

        DbService<Account> dbService = new DbServiceImpl<>(accountDao);

        final Account account = new AccountBuilder()
                .addFirstName(req.getParameter("firstName"))
                .addLastName(req.getParameter("lastName"))
                .addMiddleName(req.getParameter("middleName"))
                .addEmail(req.getParameter(EMAIL))
                .addMd5(SampleData.getMd5(req.getParameter("login")))
                .addStatus(Integer.parseInt(req.getParameter("status")) == 1)
                .build();

        int id = dbService.saveBean(account);
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
