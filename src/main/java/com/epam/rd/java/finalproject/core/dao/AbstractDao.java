package com.epam.rd.java.finalproject.core.dao;

import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {
    protected static final String SELECT_BY_ID = "";
//            "SELECT a.id `id`, first_name, last_name, middle_name, email, login, md5, status, role_ru, role_en " +
//                    "FROM account a INNER JOIN role r ON a.role_id = r.id WHERE id = ?";

    protected static final String INSERT = "";
//            "INSERT INTO account (first_name, last_name, middle_name, email, " +
//            "login, md5, status, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    protected static final String UPDATE = "";
//            "UPDATE account SET first_name = ?, last_name = ?, middle_name = ?," +
//            "email = ?, login = ?, md5 = ?, status = ?, role_id = ? WHERE id = ?";

    protected static final String SELECT_ALL = "";
//            "SELECT id, first_name, last_name, middle_name, email, " +
//            "login, md5, status, role_id FROM account";

    protected static final String SELECT_ALL_WITH_ROLE_ID_SUBSTITUTED = "";
//            "SELECT a.id, first_name, last_name, " +
//            "middle_name, email, login, md5, status, role_ru, role_en " +
//            "FROM account a INNER JOIN role r ON a.role_id = r.id";


    public SessionManager sessionManager;

    public abstract Optional<T> selectByField(Object field);

    public abstract Optional<List<T>> select();

    public abstract int insert(T data);

    public abstract int update(T data);

    public SessionManager getSessionManager() {
        return sessionManager;
    }
    protected Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
