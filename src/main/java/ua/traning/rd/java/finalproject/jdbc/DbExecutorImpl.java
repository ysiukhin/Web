package ua.traning.rd.java.finalproject.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class DbExecutorImpl<T> implements DbExecutor<T> {
    private static final Logger LOGGER = LogManager.getLogger(DbExecutorImpl.class);
    private final Class<T> daoEntity;


    public DbExecutorImpl(Class<T> daoEntity) {
        this.daoEntity = daoEntity;
    }

    public Class<T> getDaoEntity() {
        return daoEntity;
    }

    @Override
    public int executeInsert(Connection connection, String sql, Map<Integer, Object> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                if (entry.getKey() > 0) {
                    prepStatement.setObject(entry.getKey(), entry.getValue());
                }
            }
            prepStatement.executeUpdate();
            try (ResultSet rs = prepStatement.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public int executeUpdate(Connection connection, String sql, Map<Integer, Object> params) throws SQLException {
        return executeInsert(connection, sql, params);
    }

    @Override
    public List<T> executeSelect(Connection connection, String sql, Object field,
                                 BiFunction<ResultSet, Class<T>, List<T>> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            if (field != null) {
                pst.setObject(1, field);
            }
            try (ResultSet result = pst.executeQuery()) {
                return rsHandler.apply(result, daoEntity);
            }
        }
    }
}