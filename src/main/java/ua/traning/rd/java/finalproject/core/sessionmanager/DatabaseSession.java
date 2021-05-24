package ua.traning.rd.java.finalproject.core.sessionmanager;

import java.sql.Connection;

public interface DatabaseSession {
    Connection getConnection();
}

