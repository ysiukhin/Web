package com.epam.rd.java.finalproject.core.sessionmanager;

import java.sql.Connection;

public interface DatabaseSession {
    Connection getConnection();
}

