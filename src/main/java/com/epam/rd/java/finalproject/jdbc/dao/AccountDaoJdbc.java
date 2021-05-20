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

public class AccountDaoJdbc extends AccountDao {
    private static final Logger LOGGER = LogManager.getLogger(AccountDaoJdbc.class);

    private final DbExecutor<Account> dbExecutor;

    public AccountDaoJdbc(SessionManager sessionManager, DbExecutor<Account> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<Account> selectByField(Object field) {
        return Optional.ofNullable(selectQuery(field, SELECT_BY_ID).get().get(0));
    }

    @Override
    public Optional<List<Account>> select() {
        return selectQuery(null, SELECT_ALL);
    }

    private Optional<List<Account>> selectQuery(Object field, String sql) {
        try {
            return dbExecutor.executeSelect(getConnection(), sql, field,
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
                            return Collections.unmodifiableList(accounts);
                        } catch (SQLException e) {
                            LOGGER.error("AccountDaoJdbc selectQuery error sql: {}", sql);
                            throw new DaoException(e.getMessage(), e);
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public int insert(Account data) {
        try {
            return dbExecutor.executeInsert(getConnection(), INSERT,
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
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public int update(Account data) {
        try {
            return dbExecutor.executeInsert(getConnection(), UPDATE,
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
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }
}
