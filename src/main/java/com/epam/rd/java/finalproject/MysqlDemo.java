package com.epam.rd.java.finalproject;

import com.epam.rd.java.finalproject.core.dao.ProjectDao;
import com.epam.rd.java.finalproject.core.dao.RoleDao;
import com.epam.rd.java.finalproject.jdbc.dao.AccountDaoJdbc;
import com.epam.rd.java.finalproject.core.dao.AccountDao;
import com.epam.rd.java.finalproject.core.service.DbServiceAccount;
import com.epam.rd.java.finalproject.core.service.DbServiceAccountImpl;
import com.epam.rd.java.finalproject.jdbc.DbExecutorImpl;
import com.epam.rd.java.finalproject.jdbc.dao.ProjectDaoJdbc;
import com.epam.rd.java.finalproject.jdbc.dao.RoleDaoJdbc;
import com.epam.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import com.epam.rd.java.finalproject.mysql.DataSourceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class MysqlDemo {

    private static final Logger LOGGER = LogManager.getLogger("MysqlDemo");

    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {
        DataSource dataSource = new DataSourceImpl();
//        MysqlDemo demo = new MysqlDemo();

        SampleData.createTable(dataSource, System.getProperty("user.dir"));

        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);

//        DbExecutorImpl<Account> dbExecutor = new DbExecutorImpl<>();

        RoleDao roleDao = new RoleDaoJdbc(sessionManagerJdbc, new DbExecutorImpl<>());
        AccountDao accountDao = new AccountDaoJdbc(sessionManagerJdbc, new DbExecutorImpl<>());
        ProjectDao projectDao = new ProjectDaoJdbc(sessionManagerJdbc, new DbExecutorImpl<>());

        SampleData.insertTestRoles(roleDao); // Only 3 Roles can be

        // add 1 CEO/Administrator
        SampleData.insertTestAccounts(accountDao, 1, System.getProperty("user.dir"), 1);
        // add 10 Manager
        SampleData.insertTestAccounts(accountDao, 10, System.getProperty("user.dir"), 2);
        // add 100 Executprs
        SampleData.insertTestAccounts(accountDao, 100, System.getProperty("user.dir"), 3);

        SampleData.insertTestProjects(projectDao, 5, System.getProperty("user.dir"));


//        DbServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);
//        LOGGER.info(dbServiceAccount.getAllAccounts());
    }

}
