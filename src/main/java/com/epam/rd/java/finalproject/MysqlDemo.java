package com.epam.rd.java.finalproject;

import com.epam.rd.java.finalproject.core.dao.AbstractDao;
import com.epam.rd.java.finalproject.core.dao.AccountSql;
import com.epam.rd.java.finalproject.core.dao.ProjectSql;
import com.epam.rd.java.finalproject.core.dao.RoleSql;
import com.epam.rd.java.finalproject.core.model.Account;
import com.epam.rd.java.finalproject.core.model.Project;
import com.epam.rd.java.finalproject.core.model.Role;
import com.epam.rd.java.finalproject.core.service.DbServiceRole;
import com.epam.rd.java.finalproject.core.service.DbServiceRoleImpl;
import com.epam.rd.java.finalproject.jdbc.dao.AbstractDaoJdbc;
import com.epam.rd.java.finalproject.jdbc.DbExecutorImpl;
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

//        AbstractDao<Role> roleDao = new AbstractDaoJdbc<>(sessionManagerJdbc, new DbExecutorImpl<>(Role.class), new RoleSql(){});
        AbstractDao<Role> roleDao = new AbstractDaoJdbc<>(sessionManagerJdbc, new DbExecutorImpl<>(Role.class), new RoleSql() {
        });
//        RoleDao roleDao = new RoleDaoJdbc(sessionManagerJdbc, new DbExecutorImpl<>(Role.class));
//        AbstractDao<Account> accountDao = new AccountDaoJdbc(sessionManagerJdbc, new DbExecutorImpl<>(Account.class), new AccountSql(){});
        AbstractDao<Account> accountDao = new AbstractDaoJdbc<>(sessionManagerJdbc, new DbExecutorImpl<>(Account.class), new AccountSql() {
        });
//        AbstractDao<Project> projectDao = new ProjectDaoJdbc(sessionManagerJdbc, new DbExecutorImpl<>(Project.class), new ProjectSql(){});
        AbstractDao<Project> projectDao = new AbstractDaoJdbc<>(sessionManagerJdbc, new DbExecutorImpl<>(Project.class), new ProjectSql() {
        });

        SampleData.insertTestRoles(roleDao); // Only 3 Roles can be

        // add 1 CEO/Administrator
        SampleData.insertTestAccounts(accountDao, 1, System.getProperty("user.dir"), 1);
        // add 10 Manager
        SampleData.insertTestAccounts(accountDao, 10, System.getProperty("user.dir"), 2);
        // add 100 Executprs
        SampleData.insertTestAccounts(accountDao, 100, System.getProperty("user.dir"), 3);

        SampleData.insertTestProjects(projectDao, 5, System.getProperty("user.dir"));

        DbServiceRole dbService = new DbServiceRoleImpl(roleDao);
        LOGGER.info(dbService.getAllRoles());
    }

}
