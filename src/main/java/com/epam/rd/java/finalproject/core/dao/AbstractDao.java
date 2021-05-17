package com.epam.rd.java.finalproject.core.dao;

import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {
    public SessionManager sessionManager;

    public abstract Optional<T> selectByField(Object field);

    public abstract Optional<List<T>> selectAll(Object field);

    public abstract int insert(T data);

    public abstract int update(T data);

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    protected Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
