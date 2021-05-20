package com.epam.rd.java.finalproject;

import com.epam.rd.java.finalproject.core.dao.AbstractDao;
import com.epam.rd.java.finalproject.core.model.*;
import com.epam.rd.java.finalproject.core.service.*;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SampleData {
    private static final Logger LOGGER = LogManager.getLogger("MysqlDemo");
    private static final SecureRandom secureRandom = new SecureRandom();

    public static void insertTestProjects(AbstractDao<Project> projectDao, int projectsQuantity, String path) throws IOException {
        List<String> project_names =
                Files.lines(Paths.get(path + File.separator + "text" + File.separator + "project_names_65_en.txt"),
                        StandardCharsets.UTF_8).collect(Collectors.toList());

        List<String> project_desk =
                Files.lines(Paths.get(path + File.separator + "text" + File.separator + "project_desk_50_en.txt"),
                        StandardCharsets.UTF_8).collect(Collectors.toList());

        DbServiceProject dbServiceProject = new DbServiceProjectImpl(projectDao);

        for (int i = 0; i < projectsQuantity; i++) {
            int randomProject = secureRandom.nextInt(50);
            String name = project_names.get(randomProject);
            String desk = project_desk.get(randomProject);

            int id = dbServiceProject.saveProject(
                    new ProjectBuilder()
                            .addProjectName(name)
                            .addProjectDesc(desk)
                            .addStatus(true)
                            .build()
            );
            LOGGER.trace("inserted account id: {}", id);
        }
    }


    public static void createTable(DataSource dataSource, String path) throws IOException, SQLException {
        Path createDbSql = Paths.get(path + File.separator + "sql" + File.separator + "db-create-auto.sql");
        Path insertRolesSql = Paths.get(path + File.separator + "sql" + File.separator + "fill_roles.sql");
        try (Connection connection = dataSource.getConnection();
             Statement createTables = connection.createStatement()) {
            connection.setAutoCommit(false);
            createTables.executeUpdate(new String(Files.readAllBytes(createDbSql), StandardCharsets.UTF_8));
            LOGGER.trace("DB created successfully");
            createTables.executeUpdate(new String(Files.readAllBytes(insertRolesSql), StandardCharsets.UTF_8));
            LOGGER.trace("Roles inserted");
            connection.commit();
        } catch (IOException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }


    //    static void insertTestRoles(RoleDao roleDao) {
    static void insertTestRoles(AbstractDao<Role> roleDao) {
        DbServiceRole dbServiceRole = new DbServiceRoleImpl(roleDao);
        Role role = new Role();
        role.setRoleRu("Исполнительный Директор");
        role.setRoleEn("Chief Executive Officer");
        int id = dbServiceRole.saveRole(role);
        LOGGER.trace("inserted {} id: {}", role, id);

        role.setRoleRu("Менеджер");
        role.setRoleEn("Manager");
        id = dbServiceRole.saveRole(role);
        LOGGER.trace("inserted {} id: {}", role, id);

        role.setRoleRu("Исполнитель");
        role.setRoleEn("Executor");
        id = dbServiceRole.saveRole(role);
        LOGGER.trace("inserted {} id: {}", role, id);
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
            LOGGER.error(e.getMessage(), e);
//            throw e;
        }
        return null;
    }

    public static void insertTestAccounts(AbstractDao<Account> accountDao, int accountQuantity, String path, int roleId)
            throws IOException, NoSuchAlgorithmException {
//
//        SecureRandom secureRandom = new SecureRandom();
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
            int random = secureRandom.nextInt(accountQuantity * 6);
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
            LOGGER.trace("inserted account id: {}", id);
        }
    }
}