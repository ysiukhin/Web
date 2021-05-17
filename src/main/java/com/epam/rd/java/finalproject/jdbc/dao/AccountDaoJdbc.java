package com.epam.rd.java.finalproject.jdbc.dao;


import com.epam.rd.java.finalproject.core.dao.AccountDao;
import com.epam.rd.java.finalproject.core.dao.DaoException;
import com.epam.rd.java.finalproject.core.model.Account;
import com.epam.rd.java.finalproject.core.model.AccountBuilder;

import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import com.epam.rd.java.finalproject.jdbc.DbExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

import java.util.*;

import static com.epam.rd.java.finalproject.jdbc.dao.AccountDaoSql.*;

public class AccountDaoJdbc extends AccountDao {
    private static final Logger logger = LogManager.getLogger(AccountDaoJdbc.class);

    private final DbExecutor<Account> dbExecutor;

    public AccountDaoJdbc(SessionManager sessionManager, DbExecutor<Account> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }


    @Override
    public Optional<Account> selectByField(Object field) {
        try {
            return dbExecutor.executeSelect(getConnection(), selectById,
                    field, rs -> {
                        try {
                            if (rs.next()) {
                                return new AccountBuilder()
                                        .addId(rs.getInt("id"))
                                        .addFirstName(rs.getString("first_name"))
                                        .addLastName(rs.getString("last_name"))
                                        .addMiddleName(rs.getString("middle_name"))
                                        .addEmail(rs.getString("email"))
                                        .addLogin(rs.getString("login"))
                                        .addMd5(rs.getString("md5"))
                                        .addStatus(rs.getInt("status") == 1)
                                        .build();
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Account>> selectAll(Object field) {
        try {
            return dbExecutor.executeSelectAll(getConnection(), selectAllAccounts, field,
                    rs -> {
                        try {
                            List<Account> accounts = new ArrayList<>();
                            while (rs.next()) {
                                accounts.add(
                                        new AccountBuilder()
                                                .addId(rs.getInt("id"))
                                                .addFirstName(rs.getString("first_name"))
                                                .addLastName(rs.getString("last_name"))
                                                .addMiddleName(rs.getString("middle_name"))
                                                .addEmail(rs.getString("email"))
                                                .addLogin(rs.getString("login"))
                                                .addMd5(rs.getString("md5"))
                                                .addStatus(rs.getInt("status") == 1)
                                                .build()
                                );
                            }
                            return accounts;
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    }
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public int insert(Account data) {
        try {
            return dbExecutor.executeInsert(getConnection(), insertNewAccount,
                    Collections.unmodifiableList(Arrays.asList(
                            data.getFirstName(),
                            data.getLastName(),
                            data.getMiddleName(),
                            data.getEmail(),
                            data.getLogin(),
                            data.getMd5(),
                            data.getStatus(),
                            data.getRoleId())
                    )
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public int update(Account data) {
        try {
            return dbExecutor.executeInsert(getConnection(), updateAccount,
                    Collections.unmodifiableList(Arrays.asList(
                            data.getFirstName(),
                            data.getLastName(),
                            data.getMiddleName(),
                            data.getEmail(),
                            data.getLogin(),
                            data.getMd5(),
                            data.getStatus(),
                            data.getRoleId())
                    )
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }
}
