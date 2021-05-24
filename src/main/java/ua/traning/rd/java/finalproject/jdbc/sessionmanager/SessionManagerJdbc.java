package ua.traning.rd.java.finalproject.jdbc.sessionmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.sessionmanager.DatabaseSession;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManagerException;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerJdbc implements SessionManager {
    private static final Logger LOGGER = LogManager.getLogger(SessionManagerJdbc.class);
    private static final int TIMEOUT_IN_SECONDS = 0;
    private final DataSource dataSource;
    private Connection connection;
    private DatabaseSession databaseSession;

    public SessionManagerJdbc(DataSource dataSource) {
        LOGGER.info("SessionManagerJdbc()");
        if (dataSource == null) {
            throw new SessionManagerException("Datasource is null");
        }
        this.dataSource = dataSource;
    }

    @Override
    public void beginSession() {
        LOGGER.info("SessionManagerJdbc.beginSession()");
        try {
            connection = dataSource.getConnection();
            databaseSession = new DatabaseSessionJdbc(connection);
            LOGGER.info("connection: {}", connection);
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void commitSession() {
        LOGGER.info("SessionManagerJdbc.commitSession()");
        checkConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void rollbackSession() {
        checkConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void close() {
        LOGGER.info("SessionManagerJdbc.close");
        checkConnection();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public DatabaseSession getCurrentSession() {
        LOGGER.info("SessionManagerJdbc.getCurrentSession");
        checkConnection();
        return databaseSession;
    }

    private void checkConnection() {
        try {
            if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS)) {
//            if (connection == null) {
                throw new SessionManagerException("Connection is invalid");
            }
        } catch (SQLException ex) {
            throw new SessionManagerException(ex);
        }
    }
}
