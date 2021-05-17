package com.epam.rd.java.finalproject;

import com.epam.rd.java.finalproject.core.model.Account;
import com.epam.rd.java.finalproject.jdbc.dao.AccountDaoJdbc;
import com.epam.rd.java.finalproject.core.dao.AccountDao;
import com.epam.rd.java.finalproject.core.model.AccountBuilder;
import com.epam.rd.java.finalproject.core.service.DbServiceAccount;
import com.epam.rd.java.finalproject.core.service.DbServiceAccountImpl;
import com.epam.rd.java.finalproject.jdbc.DbExecutorImpl;
import com.epam.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import com.epam.rd.java.finalproject.mysql.DataSourceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

public class MysqlDemo {

    private static final Logger logger = LogManager.getLogger("MysqlDemo");

    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {
        DataSource dataSource = new DataSourceImpl();
//        MysqlDemo demo = new MysqlDemo();

        createTable(dataSource, System.getProperty("user.dir"));

        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<Account> dbExecutor = new DbExecutorImpl<>();

        AccountDao accountDao = new AccountDaoJdbc(sessionManagerJdbc, dbExecutor);

        insertTestAccounts(accountDao, 5, System.getProperty("user.dir"));
        DbServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);


        logger.info(dbServiceAccount.getAllAccounts());


//        DbServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);
//        int id = dbServiceAccount.saveAccount(new AccountBuilder()
//                .addFirstName("James")
//                .addLastName("Smith")
//                .addMiddleName("William")
//                .addEmail("james_smith_account@gmail.com")
//                .addLogin("james")
//                .addMd5("MD5('james')")
//                .addStatus(true)
//                .addRoleId(3)
//                .build()
//        );
//        Optional<Account> account = dbServiceAccount.geAccount(id);
//        logger.info("getgeAccount({}): {}",id, account.get());
    }

    //    public static String getMd5(String input) throws NoSuchAlgorithmException {
    public static String getMd5(String input) {
        MessageDigest messageDigest;
        byte[] bytesEncoded;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = messageDigest.digest();
            return new BigInteger(1, bytesEncoded).toString(16);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
//            throw e;
        }
        return null;
    }

    public static void insertTestAccounts(AccountDao accountDao, int accountQuantity, String path) throws IOException, NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        List<String> names =
                Files.lines(Paths.get(path + File.separator + "text" + File.separator + "female_names_300.txt"), StandardCharsets.UTF_8)
                        .collect(Collectors.toList());

        Collections.addAll(names, Files.lines(Paths.get(path + File.separator + "text" + File.separator + "male_names_300.txt"),
                StandardCharsets.UTF_8)
                .toArray(String[]::new));

        Collections.shuffle(names, secureRandom);

        List<String> lastNames =
                Files.lines(Paths.get(path + File.separator + "text" + File.separator + "last_names_660.txt"), StandardCharsets.UTF_8)
                        .collect(Collectors.toList());

        DbServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);

        for (int i = 0; i < accountQuantity; i++) {
            int random = secureRandom.nextInt(600);
            String name = names.get(random);
            String lastName = lastNames.get(random);
            String email = String.format("%s_%s_account@gmail.com", name.toLowerCase(), lastName.toLowerCase());
            int id = dbServiceAccount.saveAccount(
                    new AccountBuilder()
                            .addFirstName(name)
                            .addLastName(lastName)
                            .addMiddleName(name)
                            .addEmail(email)
                            .addLogin(email)
                            .addMd5(getMd5(email))
                            .addStatus(true)
                            .build()
            );
            logger.trace("inserted account id: {}", id);
        }
    }


    public static void createTable(DataSource dataSource, String path) throws IOException, SQLException {
        Path createDbSql = Paths.get(path + File.separator + "sql" + File.separator + "db-create-auto.sql");
        Path insertRolesSql = Paths.get(path + File.separator + "sql" + File.separator + "fill_roles.sql");
        try (Connection connection = dataSource.getConnection();
             Statement createTables = connection.createStatement()) {
            connection.setAutoCommit(false);
            createTables.executeUpdate(new String(Files.readAllBytes(createDbSql), StandardCharsets.UTF_8));
            logger.trace("DB created successfully");
            createTables.executeUpdate(new String(Files.readAllBytes(insertRolesSql), StandardCharsets.UTF_8));
            logger.trace("Roles inserted");
            connection.commit();
        } catch (IOException | SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }


}
