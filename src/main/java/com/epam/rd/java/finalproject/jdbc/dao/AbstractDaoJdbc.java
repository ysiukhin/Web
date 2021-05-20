package com.epam.rd.java.finalproject.jdbc.dao;

import com.epam.rd.java.finalproject.core.annotation.TableField;
import com.epam.rd.java.finalproject.core.dao.AbstractDao;
import com.epam.rd.java.finalproject.core.dao.AbstractSql;
import com.epam.rd.java.finalproject.core.dao.DaoException;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import com.epam.rd.java.finalproject.jdbc.DbExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

public class AbstractDaoJdbc<T> extends AbstractDao<T> {

    private final Logger logger = LogManager.getLogger(getClass());
    private final DbExecutor<T> dbExecutor;
    private final AbstractSql sql;

    public AbstractDaoJdbc(SessionManager sessionManager, DbExecutor<T> dbExecutor, AbstractSql sql) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.sql = sql;
    }

    @Override
    public Optional<T> selectByField(Object field) {
        return selectQuery(field, sql.selectById()).map(list -> list.get(0));
    }

    @Override
    public Optional<List<T>> select() {
        return selectQuery(null, sql.selectAll());
    }

    //    private Optional<List<T>> selectQuery(Object field, String sql) {
//        try {
//            return dbExecutor.executeSelect(getConnection(), sql, field,
//                    rs -> {
//                        try {
//
//                            List<T> resultList = new ArrayList<>();
//                            while (rs.next()) {
//
////                                T role = new Role();
////                                role.setId(rs.getInt("id"));
////                                role.setRoleRu(rs.getString("role_ru"));
////                                role.setRoleEn(rs.getString("role_en"));
////                                resultList.add(role);
//                            }
//                            return resultList;
//                        } catch (SQLException e) {
//                            logger.error(e.getMessage(), e);
//                        }
//                        return null;
//                    }
//            );
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//        return Optional.empty();
//    }
    private Optional<List<T>> selectQuery(Object field, String sql) {
        try {
            return dbExecutor.executeSelectBi(getConnection(), sql, field,
                    (rs, clazz) -> {
                        try {
                            List<T> resultList = new ArrayList<>();
                            while (rs.next()) {
                                T bean = clazz.newInstance();
                                for (Field beanField : clazz.getDeclaredFields()) {
                                    beanField.setAccessible(true);
                                    if (beanField.isAnnotationPresent(TableField.class)) {
                                        TableField tableField = beanField.getAnnotation(TableField.class);
                                        beanField.set(bean, rs.getObject(tableField.value()));
                                    }
                                    beanField.setAccessible(false);
                                }
                                resultList.add(bean);
                            }
                            return resultList;
                        } catch (SQLException | InstantiationException | IllegalAccessException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    }
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
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
            return dbExecutor.executeInsertBi(getConnection(), sql.insert(), params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
            return dbExecutor.executeUpdateBi(getConnection(), sql.update(), params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

}
