package ua.traning.rd.java.finalproject;

import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.core.dao.DbService;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.*;
import ua.traning.rd.java.finalproject.jdbc.dao.*;

import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class SampleData {
    private static final Logger LOGGER = LogManager.getLogger("MysqlDemo");
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static void init(DataSource dataSource) throws SQLException, IOException, NoSuchAlgorithmException {
//        DataSource dataSource = new DataSourceImpl();

        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);

        Dao<Kind> kindDao =
                new DaoJdbc<>(sessionManagerJdbc, Kind.class);
        Dao<Account> accountDao =
                new DaoJdbc<>(sessionManagerJdbc, Account.class);
    }


    public static void processSql(DataSource dataSource, String path, String sql) throws IOException, SQLException {
        Path createDbSql = Paths.get(path + File.separator + "sql" + File.separator + sql);
        try (Connection connection = dataSource.getConnection();
             Statement createTables = connection.createStatement()) {
            connection.setAutoCommit(false);
            createTables.executeUpdate(new String(Files.readAllBytes(createDbSql), StandardCharsets.UTF_8));
            LOGGER.trace("DB created successfully");
            connection.commit();
        } catch (IOException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }


    public static String getMd5(String input) {
        MessageDigest messageDigest;
        byte[] bytesEncoded;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = messageDigest.digest();
            return new BigInteger(1, bytesEncoded).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static void insertTestAccounts(Dao<Account> accountDao, int accountQuantity, String path)
            throws IOException {
        List<String> names_en =
                Files.lines(Paths.get(path + File.separator + "text" + File.separator + "first_names_300_en.txt"), StandardCharsets.UTF_8)
                        .collect(Collectors.toList());
        List<String> surnames_en =
                Files.lines(Paths.get(path + File.separator + "text" + File.separator + "last_names_300_en.txt"), StandardCharsets.UTF_8)
                        .collect(Collectors.toList());
        List<String> names_ru =
                Files.lines(Paths.get(path + File.separator + "text" + File.separator + "first_names_300_ru.txt"), StandardCharsets.UTF_8)
                        .collect(Collectors.toList());
        List<String> surnames_ru =
                Files.lines(Paths.get(path + File.separator + "text" + File.separator + "last_names_300_ru.txt"), StandardCharsets.UTF_8)
                        .collect(Collectors.toList());

        Collections.shuffle(names_en, SECURE_RANDOM);
        Collections.shuffle(surnames_en, SECURE_RANDOM);
        Collections.shuffle(names_ru, SECURE_RANDOM);
        Collections.shuffle(surnames_ru, SECURE_RANDOM);

        Map<String, String> accountsMap = new HashMap<>();
        for (int i = 0; i < names_en.size(); i++) {
            accountsMap.put(names_en.get(i), surnames_en.get(i));
            accountsMap.put(names_ru.get(i), surnames_ru.get(i));
        }
        List<Map.Entry<String, String>> accountsList =
                accountsMap.entrySet().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        Collections.shuffle(accountsList, SECURE_RANDOM);

        DbService<Account> dbService = new DbServiceImpl<>(accountDao);

        List<Integer> usedValues = new ArrayList<>();

        for (int i = 0; i < accountQuantity; ) {
            int random = SECURE_RANDOM.nextInt(600);
            if (!usedValues.contains(random)) {
                String name = accountsList.get(random).getKey();
                String lastName = accountsList.get(random).getValue();
                String email = String.format("%s_%s_login@timecounter.com", name.toLowerCase(), lastName.toLowerCase());
                int id = dbService.saveBean(
                        new AccountBuilder()
                                .addFirstName(name)
                                .addLastName(lastName)
                                .addMiddleName(null)
                                .addEmail(email)
                                .addMd5(getMd5(email))
                                .addStatus(true)
                                .build()
                );
                usedValues.add(random);
                i++;
            }
        }
    }

    public static void insertTestAccountActivities(DataSource dataSource, int activitiesPerAccount) {
        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);

        Dao<Account> accountDao =
                new DaoJdbc<>(sessionManagerJdbc, Account.class);
        Dao<Activity> activityDao =
                new DaoJdbc<>(sessionManagerJdbc, Activity.class);
        Dao<AccountActivity> accountActivityDao =
                new DaoJdbc<>(sessionManagerJdbc, AccountActivity.class);

        DbService<Account> dbServiceAccount = new DbServiceImpl<>(accountDao);
        DbService<Activity> dbServiceActivity = new DbServiceImpl<>(activityDao);
        DbService<AccountActivity> DbServiceAccountActivity = new DbServiceImpl<>(accountActivityDao);

        List<Account> accounts = dbServiceAccount.getAllBeans().get();
        List<Activity> activities = dbServiceActivity.getAllBeans().get();

        Collections.shuffle(accounts);
        Collections.shuffle(activities);

        for (int j = 1; j < accounts.size(); j++) {

            List<Integer> usedActivities = new ArrayList<>(activitiesPerAccount);
            for (int i = 0; i < activitiesPerAccount; i++) {
                int randomActivity = SECURE_RANDOM.nextInt(activities.size());
                if (randomActivity > 0 && !usedActivities.contains(randomActivity)) {
                    AccountActivity accountActivity = new AccountActivity();
                    accountActivity.setAccountId(accounts.get(j).getId());
                    accountActivity.setActivityId(randomActivity);
                    accountActivity.setStatus(true);
                    usedActivities.add(randomActivity);
//                    LOGGER.info("Before: {}", accountActivity);
                    DbServiceAccountActivity.saveBean(accountActivity);
//                    LOGGER.info("After: {}", accountActivity);
                }
            }
        }
    }

    public static void insertTestRequests(DataSource dataSource, int activitiesPerAccount) {
        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(dataSource);

        Dao<Account> accountDao =
                new DaoJdbc<>(sessionManagerJdbc, Account.class);
        Dao<Activity> activityDao =
                new DaoJdbc<>(sessionManagerJdbc, Activity.class);
        Dao<AccountActivity> accountActivityDao =
                new DaoJdbc<>(sessionManagerJdbc, AccountActivity.class);

        DbService<Account> dbServiceAccount = new DbServiceImpl<>(accountDao);
        DbService<Activity> dbServiceActivity = new DbServiceImpl<>(activityDao);
        DbService<AccountActivity> DbServiceAccountActivity = new DbServiceImpl<>(accountActivityDao);

        List<Account> accounts = dbServiceAccount.getAllBeans().get();
        List<Activity> activities = dbServiceActivity.getAllBeans().get();

        Collections.shuffle(accounts);
        Collections.shuffle(activities);

        for (int j = 1; j < accounts.size() / 4; j++) {
            List<Integer> usedActivities = new ArrayList<>(activitiesPerAccount);
            for (int i = 0; i < activitiesPerAccount; i++) {
                int randomActivity = SECURE_RANDOM.nextInt(activities.size());
                if (randomActivity > 0 && !usedActivities.contains(randomActivity)) {
                    AccountActivity accountActivity = new AccountActivity();
                    accountActivity.setAccountId(accounts.get(j).getId());
                    accountActivity.setActivityId(randomActivity);
                    accountActivity.setStatus(true);
                    usedActivities.add(randomActivity);
                    DbServiceAccountActivity.saveBean(accountActivity);
                }
            }
        }
    }
}