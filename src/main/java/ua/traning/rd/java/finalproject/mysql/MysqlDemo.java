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
import java.security.SecureRandom;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlDemo {

    private static final Logger LOGGER = LogManager.getLogger("MysqlDemo");
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {
        DataSource dataSource = new DataSourceImpl();
//        MysqlDemo demo = new MysqlDemo();

        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);

//        Dao<Kind> kindDao = new DaoJdbc<>(sessionManagerJdbc, Kind.class);
//        Dao<Account> accountDao =
//                new DaoJdbc<>(sessionManagerJdbc, Account.class);
//        Dao<Activity> activityDao =
//                new DaoJdbc<>(sessionManagerJdbc, Activity.class);
//        Dao<AccountActivity> accountActivityDao =
//                new DaoJdbc<>(sessionManagerJdbc, AccountActivity.class);

//        DbService<Record> recordDbService = new DbServiceImpl<>(new DaoJdbc<>(sessionManagerJdbc, Record.class));
//
//        DbService<AccountActivity> dbService =
//                new DbServiceImpl<>(new DaoJdbc<>(sessionManagerJdbc, AccountActivity.class));
//
//        List<AccountActivity> list = dbService.getAllBeans().get();


        SampleData.insertTestRecords(dataSource);

        LOGGER.info("All done");
    }
}