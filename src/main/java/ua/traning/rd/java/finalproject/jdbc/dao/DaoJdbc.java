package ua.traning.rd.java.finalproject.jdbc.dao;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableField;
import ua.traning.rd.java.finalproject.core.annotation.TableName;
import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.core.dao.DaoException;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;
import ua.traning.rd.java.finalproject.jdbc.DbExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DaoJdbc<T> extends Dao<T> {

    private static final Logger LOGGER = LogManager.getLogger(DaoJdbc.class);

    private final DbExecutor<T> dbExecutor;
    private final AbstractSql sql;

    public DaoJdbc(SessionManager sessionManager, Class<T> daoEntity) {
        super(sessionManager, daoEntity);
        dbExecutor = null;
        sql = null;
    }

    public DaoJdbc(SessionManager sessionManager, DbExecutor<T> dbExecutor, AbstractSql sql, Class<T> daoEntity) {
        super(sessionManager, daoEntity);
        this.dbExecutor = dbExecutor;
        this.sql = sql;
    }

    @Override
    public List<T> selectBy(String column, Object field) {
        try {
            T bean = getDaoEntity().newInstance();
            StringBuilder sqlQuery = new StringBuilder("SELECT ");
            StringJoiner columnList = new StringJoiner(",");
            for (Field beanField : bean.getClass().getDeclaredFields()) {
                beanField.setAccessible(true);
                if (beanField.isAnnotationPresent(TableField.class)) {
                    TableField tableField = beanField.getAnnotation(TableField.class);
                    columnList.add(tableField.dbFieldName());
//                beanField.set(bean, rs.getObject(tableField.dbFieldName()));
                }
//            else if (beanField.isAnnotationPresent(Linked.class)) { }
                beanField.setAccessible(false);
            }
            sqlQuery.append(columnList).append(" FROM ").append(bean.getClass().getAnnotation(TableName.class).dbTable());
            sqlQuery.append(" WHERE ").append(column).append("=?");
            try (PreparedStatement pst = getConnection().prepareStatement(sqlQuery.toString())) {
                if (field != null) {
                    pst.setObject(1, field);
                }
                try (ResultSet resultSet = pst.executeQuery()) {
                    List<T> resultList = resultSetProcess(resultSet);
                    return resultList;
                }
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
//            LOGGER.error("DaoJdbc<{}> SELECT construction error", getDaoEntity().getSimpleName());
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
    }
    //    @Override
//    public T selectByField(Object field) {
//
//        return selectQuery(field, sql.selectById()).get(0);
//    }

    @Override
    public List<T> select() {
//    public List<T> select__() {
        try {
//            T bean = getDaoEntity().newInstance();
//            Class<?> clazz = getDaoEntity();
            StringBuilder sqlQuery = new StringBuilder("SELECT ");
            StringJoiner columnList = new StringJoiner(",");
            for (Field beanField : getDaoEntity().getDeclaredFields()) {
                beanField.setAccessible(true);
                if (beanField.isAnnotationPresent(TableField.class)) {
                    TableField tableField = beanField.getAnnotation(TableField.class);
                    columnList.add(tableField.dbFieldName());
//                beanField.set(bean, rs.getObject(tableField.dbFieldName()));
                }
//            else if (beanField.isAnnotationPresent(Linked.class)) { }
                beanField.setAccessible(false);
            }
            sqlQuery.append(columnList).append(" FROM ").append(getDaoEntity().getAnnotation(TableName.class).dbTable());

            try (PreparedStatement pst = getConnection().prepareStatement(sqlQuery.toString())) {
                try (ResultSet resultSet = pst.executeQuery()) {
                    List<T> resultList = resultSetProcess(resultSet);
                    return resultList;
                }
            }
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
//            LOGGER.error("DaoJdbc<{}> SELECT construction error", getDaoEntity().getSimpleName());
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
    }

    private List<T> resultSetProcess(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        List<T> resultList = new ArrayList<>();
        while (resultSet.next()) {
            T bean = getDaoEntity().newInstance();
            int primaryKeyValue = 0;
            for (Field beanField : bean.getClass().getDeclaredFields()) {
                beanField.setAccessible(true);
                if (beanField.isAnnotationPresent(TableField.class)) {
                    TableField tableField = beanField.getAnnotation(TableField.class);
//                                LOGGER.info("tableField.dbFieldName(): {}",tableField.dbFieldName());
                    beanField.set(bean, resultSet.getObject(tableField.dbFieldName()));
                    if (beanField.isAnnotationPresent(PrimaryKey.class)) {
                        primaryKeyValue = beanField.getInt(bean);
                    }
                }
                if (beanField.isAnnotationPresent(Linked.class)) {
                    Linked foreignKeyColumn = beanField.getAnnotation(Linked.class);
                    ParameterizedType genericList = (ParameterizedType) beanField.getGenericType();
                    Class<?> linked = (Class<?>) genericList.getActualTypeArguments()[0];
                    Dao<?> linkedDao = new DaoJdbc<>(getSessionManager(), linked);

//                    List<?> list = linkedDao.select();
                    List<?> list = linkedDao.selectBy(foreignKeyColumn.value(), primaryKeyValue);

                    beanField.set(bean, list);
                    LOGGER.info("tableField.dbFieldName(): {}", bean);
                }
                beanField.setAccessible(false);
            }
            resultList.add(bean);
        }
        return resultList;
    }

    //        @Override
//    public List<T> select() {
    public List<T> select_() {
        try {
            T bean = getDaoEntity().newInstance();
            StringBuilder sqlQuery = new StringBuilder("SELECT ");
            StringJoiner columnList = new StringJoiner(",");
            for (Field beanField : bean.getClass().getDeclaredFields()) {
                beanField.setAccessible(true);
                if (beanField.isAnnotationPresent(TableField.class)) {
                    TableField tableField = beanField.getAnnotation(TableField.class);
                    columnList.add(tableField.dbFieldName());
//                beanField.set(bean, rs.getObject(tableField.dbFieldName()));
                }
//            else if (beanField.isAnnotationPresent(Linked.class)) { }
                beanField.setAccessible(false);
            }
            sqlQuery.append(columnList).append(" FROM ").append(bean.getClass().getAnnotation(TableName.class).dbTable());
            return selectQuery(null, sqlQuery.toString());
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("DaoJdbc<{}> SELECT construction error", getDaoEntity().getSimpleName());
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
    }

//    @Override
//    public List<T> select() {
//        return selectQuery(null, sql.selectAll());
//    }

    private List<T> selectQuery(Object field, String sql) {
        try {
            return dbExecutor.executeSelect(getConnection(), sql, field,
                    (rs, clazz) -> {
                        try {
                            List<T> resultList = new ArrayList<>();
                            while (rs.next()) {
                                T bean = clazz.newInstance();
                                for (Field beanField : clazz.getDeclaredFields()) {
                                    beanField.setAccessible(true);
                                    if (beanField.isAnnotationPresent(TableField.class)) {
                                        TableField tableField = beanField.getAnnotation(TableField.class);
                                        beanField.set(bean, rs.getObject(tableField.dbFieldName()));
                                    }
                                    beanField.setAccessible(false);
                                }
                                resultList.add(bean);
                            }
                            return resultList;
                        } catch (SQLException | InstantiationException | IllegalAccessException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                        return null;
                    }
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public int insert(T data) {
        try {

            Map<Integer, Object> params = new HashMap<>();
            for (Field beanField : data.getClass().getDeclaredFields()) {
                beanField.setAccessible(true);
                if (beanField.isAnnotationPresent(TableField.class)) {
                    TableField tableField = beanField.getAnnotation(TableField.class);
                    params.put(tableField.insertPosition(), beanField.get(data));
                }
                beanField.setAccessible(false);
            }
            return dbExecutor.executeInsert(getConnection(), sql.insert(), params);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }

    }

    @Override
    public int update(T data) {
        try {
            Map<Integer, Object> params = new HashMap<>();
            for (Field beanField : data.getClass().getDeclaredFields()) {
                beanField.setAccessible(true);
                if (beanField.isAnnotationPresent(TableField.class)) {
                    TableField tableField = beanField.getAnnotation(TableField.class);
                    params.put(tableField.updatePosition(), beanField.get(data));
                }
                beanField.setAccessible(false);
            }
            return dbExecutor.executeUpdate(getConnection(), sql.update(), params);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }
}
