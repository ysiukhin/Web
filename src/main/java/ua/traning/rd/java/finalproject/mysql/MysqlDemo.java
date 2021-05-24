package ua.traning.rd.java.finalproject.mysql;

import ua.traning.rd.java.finalproject.SampleData;
import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.core.dao.DbService;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.jdbc.dao.*;

import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;

public class MysqlDemo {

    private static final Logger LOGGER = LogManager.getLogger("MysqlDemo");

    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {
        DataSource dataSource = new DataSourceImpl();
//        MysqlDemo demo = new MysqlDemo();

        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);

        Dao<Kind> kindDao = new DaoJdbc<>(sessionManagerJdbc, Kind.class);
        Dao<Account> accountDao =
                new DaoJdbc<>(sessionManagerJdbc, Account.class);
        Dao<Activity> activityDao =
                new DaoJdbc<>(sessionManagerJdbc, Activity.class);
        Dao<AccountActivity> accountActivityDao =
                new DaoJdbc<>(sessionManagerJdbc, AccountActivity.class);

        SampleData.processSql(dataSource, System.getProperty("user.dir"), "db-create-auto.sql");
        SampleData.processSql(dataSource, System.getProperty("user.dir"), "account-test-data-fill.sql");
        SampleData.processSql(dataSource, System.getProperty("user.dir"), "kind-test-data-fill.sql");
        SampleData.processSql(dataSource, System.getProperty("user.dir"), "activity-test-data-fill.sql");
        SampleData.processSql(dataSource, System.getProperty("user.dir"), "account_activity-test-data-fill.sql");


//        SampleData.insertTestAccountActivities(dataSource, 2);
        DbService<AccountActivity> dbServiceAccountActivities = new DbServiceImpl<>(accountActivityDao);
        DbService<Activity> dbServiceActivity = new DbServiceImpl<>(activityDao);
        Activity testActivity = dbServiceActivity.getBeansById(5);
        testActivity.setActivityEn("test");
        testActivity.setActivityRu("тест");

        dbServiceActivity.updateBean(testActivity);

//        List<AccountActivity> accountActivityList = dbServiceAccountActivities.getAllBeans();
        List<Activity> activityList = dbServiceActivity.getAllBeans();
//        DbService<Kind> dbServiceAccountActivities = new DbServiceImpl<>(kindDao);
//        List<Kind> accountActivityList = dbServiceAccountActivities.getAllBeans();

        LOGGER.info("All done");
    }
}