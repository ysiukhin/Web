package ua.traning.rd.java.finalproject.core.sessionmanager;

import java.sql.Connection;

public interface SessionManager extends AutoCloseable {
    void beginSession();

    void commitSession();

    void rollbackSession();

    void close();

    Connection getConnection();

}
