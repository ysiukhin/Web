package com.epam.rd.java.finalproject.jdbc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author sergey
 * created on 03.02.19.
 */

public class DbExecutorImpl<T> implements DbExecutor<T> {
    private static final Logger logger = LogManager.getLogger(DbExecutorImpl.class);
    private final Class<T> daoEntity;

    public DbExecutorImpl(Class<T> daoEntity) {
        this.daoEntity = daoEntity;
    }


    @Override
    public int executeInsertBi(Connection connection, String sql, Map<Integer, Object> params) throws SQLException {
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
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public int executeUpdateBi(Connection connection, String sql, Map<Integer, Object> params) throws SQLException {
        return executeInsertBi(connection, sql, params);
    }

    @Override
    public int executeInsert(Connection connection, String sql, List<Object> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int index = 0; index < params.size(); index++) {
                prepStatement.setObject(index + 1, params.get(index));
            }
            prepStatement.executeUpdate();
            try (ResultSet rs = prepStatement.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public int executeUpdate(Connection connection, String sql, List<Object> params) throws SQLException {
        return executeInsert(connection, sql, params);
    }

    @Override
    public Optional<List<T>> executeSelectBi(Connection connection, String sql, Object field,
                                             BiFunction<ResultSet, Class<T>, List<T>> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            if (field != null) {
                pst.setObject(1, field);
            }
            try (ResultSet result = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(result, daoEntity));
            }
        }
    }

//    @Override
//    public Optional<T> executeSelect(Connection connection, String sql, Object field,
//                                     Function<ResultSet, T> rsHandler) throws SQLException {
//        try (PreparedStatement pst = connection.prepareStatement(sql)) {
//            pst.setObject(1, field);
//            try (ResultSet rs = pst.executeQuery()) {
//                return Optional.ofNullable(rsHandler.apply(rs));
//            }
//        }
//    }

    @Override
    public Optional<List<T>> executeSelect(Connection connection, String sql, Object field,
                                           Function<ResultSet, List<T>> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            if (field != null) {
                pst.setObject(1, field);
            }
            try (ResultSet result = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(result));
            }
        }
    }
}
