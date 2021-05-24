package ua.traning.rd.java.finalproject.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public interface DbExecutor<T> {
    int executeInsert(Connection connection, String sql, Map<Integer, Object> params) throws SQLException;

    int executeUpdate(Connection connection, String sql, Map<Integer, Object> params) throws SQLException;

    List<T> executeSelect(Connection connection, String sql, Object id,
                          BiFunction<ResultSet, Class<T>, List<T>> rsHandler) throws SQLException;
}
