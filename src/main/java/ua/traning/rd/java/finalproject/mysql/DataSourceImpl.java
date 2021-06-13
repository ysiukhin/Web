package ua.traning.rd.java.finalproject.mysql;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceImpl implements DataSource {

    private static final String URL = "jdbc:mysql://" +           //db type
            "localhost:" +              //host name
            "3306/" +                   //port
            "timecounterdb?" +          //db name
            "autoReconnect=true&" +     //
            "allowMultiQueries=true&" + // allow execute batch sql script
            "useSSL=false&" +           //do not use ssl
            "user=web&" +               //login
            "password=password";        //password

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    @Override
    public PrintWriter getLogWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) {
        throw new UnsupportedOperationException();

    }

    @Override
    public int getLoginTimeout() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.logging.Logger getParentLogger() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        throw new UnsupportedOperationException();
    }
}
