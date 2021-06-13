package ua.traning.rd.java.finalproject.jdbc.dao;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;
import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.servlet.exception.DaoException;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static ua.traning.rd.java.finalproject.Constants.ID;
import static ua.traning.rd.java.finalproject.Constants.SQL_LIMIT_OFFSET_BOUNDS;

public class DaoJdbc<T> extends Dao<T> {

    private static final Logger LOGGER = LogManager.getLogger(DaoJdbc.class);

    public DaoJdbc(SessionManager sessionManager, Class<T> daoEntity) {
        super(sessionManager, daoEntity);
    }

    @Override
    public List<T> selectByRecordNumberInRange(int limit, int offset) {
        return selectByRecordNumberInRange(limit, offset, (buildSelect() + SQL_LIMIT_OFFSET_BOUNDS));
    }

    @Override
    public List<T> selectByRecordNumberInRange(int limit, int offset, String sqlQuery) {
        try {
            try (PreparedStatement pst = getConnection().prepareStatement(sqlQuery)) {
                pst.setInt(1, limit);
                pst.setInt(2, offset);
                try (ResultSet resultSet = pst.executeQuery()) {
                    return selectResultSetProcess(resultSet);
                }
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            LOGGER.error("sql: {} message: {}", sqlQuery, e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> selectBy(List<String> columns, List<Object> values) {
        String sqlQuery = buildSelect().concat(columns.stream().map(str -> str.concat("=?"))
                .collect(Collectors.joining(",", " WHERE ", "")));
        return selectQuery(values, sqlQuery);
    }

    @Override
    public List<T> selectBy(String sqlQuery, List<Object> values) {
        return selectQuery(values, sqlQuery);
    }

    private List<T> selectQuery(List<Object> values, String sqlQuery) {
        try {
            try (PreparedStatement pst = getConnection().prepareStatement(sqlQuery)) {
                for (int i = 0; i < values.size(); i++) {
                    pst.setObject(i + 1, values.get(i));
                }
                try (ResultSet resultSet = pst.executeQuery()) {
                    return selectResultSetProcess(resultSet);
                }
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            LOGGER.error("sql: {} message: {}", sqlQuery, e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> call(String storedProc, List<Object> values) {
        try {
            try (CallableStatement callStatement = getConnection().prepareCall(storedProc)) {
                for (int i = 0; i < values.size(); i++) {
                    callStatement.setObject(i + 1, values.get(i));
                }
                try (ResultSet resultSet = callStatement.executeQuery()) {
                    return selectResultSetProcess(resultSet);
                }
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            LOGGER.error("stored proc Name: {} message: {}", storedProc, e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
    }

    private String buildSelect() {
        StringBuilder sqlQuery = new StringBuilder("SELECT ");
        StringJoiner columnList = new StringJoiner(",");
        for (Field beanField : getDaoEntity().getDeclaredFields()) {
            beanField.setAccessible(true);
            if (beanField.isAnnotationPresent(TableColumn.class)) {
                TableColumn tableColumn = beanField.getAnnotation(TableColumn.class);
                columnList.add(tableColumn.value());
            }
            beanField.setAccessible(false);
        }
        sqlQuery.append(columnList);
        return sqlQuery.append(" FROM ").append(getDaoEntity()
                .getAnnotation(TableName.class).dbTable()).toString();
    }

    private List<T> selectResultSetProcess(ResultSet resultSet)
            throws SQLException, IllegalAccessException, InstantiationException {
        List<T> resultList = new ArrayList<>();
        while (resultSet.next()) {
            T bean = getDaoEntity().newInstance();
            int primaryKeyValue = 0;
            for (Field beanField : bean.getClass().getDeclaredFields()) {
                beanField.setAccessible(true);
                if (beanField.isAnnotationPresent(TableColumn.class)) {
                    TableColumn tableColumn = beanField.getAnnotation(TableColumn.class);
                    beanField.set(bean, resultSet.getObject(tableColumn.value()));
                    if (beanField.isAnnotationPresent(PrimaryKey.class)) {
                        primaryKeyValue = beanField.getInt(bean);
                    }
                }
                if (beanField.isAnnotationPresent(Linked.class)
                        && beanField.getAnnotation(Linked.class).strategy().equals("active")) {
                    Linked foreignKeyColumn = beanField.getAnnotation(Linked.class);
                    ParameterizedType genericList = (ParameterizedType) beanField.getGenericType();
                    Class<?> linked = (Class<?>) genericList.getActualTypeArguments()[0];
                    Dao<?> linkedDao = new DaoJdbc<>(getSessionManager(), linked);
                    List<?> list = linkedDao.selectBy(Collections.singletonList(foreignKeyColumn.value()),
                            Collections.singletonList(primaryKeyValue));
                    beanField.set(bean, list);
                }
                beanField.setAccessible(false);
            }
            resultList.add(bean);
        }
        return resultList;
    }

    public Map<String, List<Object>> buildInsert(T entity)
            throws IllegalAccessException {
        StringBuilder sqlQuery = new StringBuilder("INSERT INTO ");
        sqlQuery.append(getDaoEntity().getAnnotation(TableName.class).dbTable());
        StringJoiner columnList = new StringJoiner(",");
        StringJoiner parameterList = new StringJoiner(",");
        List<Object> params = new ArrayList<>();
        for (Field beanField : getDaoEntity().getDeclaredFields()) {
            beanField.setAccessible(true);
            if (beanField.isAnnotationPresent(TableColumn.class)) {
                TableColumn tableColumn = beanField.getAnnotation(TableColumn.class);
                if (!beanField.isAnnotationPresent(PrimaryKey.class)) {
                    columnList.add(tableColumn.value());
                    parameterList.add("?");
                    params.add(beanField.get(entity));
                }
            }
            beanField.setAccessible(false);
        }
        sqlQuery.append(" (").append(columnList)
                .append(") VALUES (").append(parameterList).append(")");
        return Collections.singletonMap(sqlQuery.toString(), params);
    }

    @Override
    public int insert(T entity) {
        Savepoint savePoint = null;
        try {
            Map<String, List<Object>> insertParameters = buildInsert(entity);
            List<Object> params = new ArrayList<>(insertParameters.values()).get(0);
            String sqlQuery = insertParameters.keySet().toArray(new String[0])[0];
            savePoint = getConnection().setSavepoint("savePointName");
            try (PreparedStatement prepStatement =
                         getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.size(); i++) {
                    prepStatement.setObject(i + 1, params.get(i));
                }
                prepStatement.executeUpdate();
                try (ResultSet rs = prepStatement.getGeneratedKeys()) {
                    rs.next();
                    int generatedKey = rs.getInt(1);
                    Field primaryKey = getDaoEntity().getDeclaredField(ID);
                    primaryKey.setAccessible(true);
                    primaryKey.set(entity, generatedKey);
                    primaryKey.setAccessible(false);
                    return generatedKey;
                }
            }
        } catch (Exception e) {
            LOGGER.error("INSERT error: {}", e.getMessage(), e);
            try {
                getConnection().rollback(savePoint);
            } catch (SQLException ex) {
                LOGGER.error("INSERT rollback error: {}", e.getMessage(), e);
            }
            throw new DaoException(e);
        }
    }

    private Map<String, List<Object>> buildUpdate(T entity)
            throws IllegalAccessException {
        StringBuilder sqlQuery = new StringBuilder("UPDATE ");
        sqlQuery.append(getDaoEntity().getAnnotation(TableName.class).dbTable());
        sqlQuery.append(" SET ");
        StringJoiner columnList = new StringJoiner(",");
        StringBuilder whereClause = new StringBuilder(" WHERE ");
        List<Object> params = new ArrayList<>();
        Object where = null;
        for (Field beanField : getDaoEntity().getDeclaredFields()) {
            beanField.setAccessible(true);
            if (beanField.isAnnotationPresent(TableColumn.class)) {
                TableColumn tableColumn = beanField.getAnnotation(TableColumn.class);
                if (beanField.isAnnotationPresent(PrimaryKey.class)) {
                    whereClause.append(tableColumn.value()).append("=").append("?");
                    where = beanField.get(entity);
                } else {
                    StringBuilder oneColumnData = new StringBuilder(tableColumn.value());
                    oneColumnData.append("=").append("?");
                    columnList.add(oneColumnData);
                    params.add(beanField.get(entity));
                }
            }
            beanField.setAccessible(false);
        }
        params.add(where);
        sqlQuery.append(columnList).append(whereClause);
        return Collections.singletonMap(sqlQuery.toString(), params);
    }

    @Override
    public int update(T entity) {
        Savepoint savePoint = null;
        try {
            Map<String, List<Object>> updateParameters = buildUpdate(entity);
            List<Object> params = new ArrayList<>(updateParameters.values()).get(0);
            String sqlQuery = updateParameters.keySet().toArray(new String[0])[0];
            savePoint = getConnection().setSavepoint("savePointName");
            try (PreparedStatement prepStatement =
                         getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.size(); i++) {
                    prepStatement.setObject(i + 1, params.get(i));
                }
                return prepStatement.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            try {
                getConnection().rollback(savePoint);
            } catch (SQLException ex) {
                LOGGER.error(e.getMessage(), e);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public int update(String sqlQuery, List<Object> values) {
        Savepoint savePoint = null;
        try {
            savePoint = getConnection().setSavepoint("savePointName");
            try (PreparedStatement prepStatement =
                         getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < values.size(); i++) {
                    prepStatement.setObject(i + 1, values.get(i));
                }
                return prepStatement.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            try {
                getConnection().rollback(savePoint);
            } catch (SQLException ex) {
                LOGGER.error(e.getMessage(), e);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public int delete(int id) {
        return delete(Collections.singletonList(ID), Collections.singletonList(id));
    }

    @Override
    public int delete(List<String> columns, List<Object> values) {
        String sqlQuery = columns.stream().map(str -> str.concat("=?"))
                .collect(Collectors.joining(" AND "
                        , "DELETE FROM " + getDaoEntity().getAnnotation(TableName.class).dbTable() + " WHERE "
                        , ""));
        Savepoint savePoint = null;
        try {
            savePoint = getConnection().setSavepoint("savePointName");
            try (PreparedStatement prepStatement =
                         getConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < values.size(); i++) {
                    prepStatement.setObject(i + 1, values.get(i));
                }
                return prepStatement.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            try {
                getConnection().rollback(savePoint);
            } catch (SQLException ex) {
                LOGGER.error(e.getMessage(), e);
            }
            throw new DaoException(e);
        }
    }

    @Override
    public int size() {
        return size("SELECT count(*) FROM " + getDaoEntity().getAnnotation(TableName.class).dbTable());
    }

    @Override
    public int size(String sqlQuery) {
        try (PreparedStatement pst = getConnection().prepareStatement(sqlQuery);
             ResultSet resultSet = pst.executeQuery()) {
            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
    }
}
