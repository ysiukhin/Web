package ua.traning.rd.java.finalproject.core.dao;

import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;

import java.sql.Connection;
import java.util.List;

public abstract class Dao<T> {
    private SessionManager sessionManager;
    private final Class<T> daoEntity;

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Dao(SessionManager sessionManager, Class<T> daoEntity) {
        this.sessionManager = sessionManager;
        this.daoEntity = daoEntity;
    }

    public Class<T> getDaoEntity() {
        return daoEntity;
    }

    public abstract List<T> selectBy(List<String> column, List<Object> fields);

    public abstract List<T> selectBy(String sqlQuery, List<Object> values);

    public abstract List<T> call(String storedProc, List<Object> values);

    public abstract List<T> selectByRecordNumberInRange(int limit, int offset);

    public abstract List<T> selectByRecordNumberInRange(int limit, int offset, String sqlQuery);

    public abstract int insert(T data);

    public abstract int update(T data);

    public abstract int update(String sqlQuery, List<Object> values);

    public abstract int delete(int id);

    public abstract int delete(List<String> columns, List<Object> values);

    public abstract int size();

    public abstract int size(String sqlQuery);

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    protected Connection getConnection() {
        return sessionManager.getConnection();
    }
}
