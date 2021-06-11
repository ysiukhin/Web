package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static ua.traning.rd.java.finalproject.Constants.EMPTY_RESULT;

public class EntityListService<T> {
    public final static Logger LOGGER = LogManager.getLogger(EntityListService.class);
    private final Class<T> entityClass;
    private final DataSource dataSource;

//    public EntityListService(Class<T> entityClass) {
//        this.entityClass = entityClass;
//        this.dataSource = Servlet.dataSource;
//    }

    public EntityListService(Class<T> entityClass, DataSource dataSource) {
        this.entityClass = entityClass;
        this.dataSource = dataSource;
    }

    public int totalEntityQuantity() {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), entityClass)).beanQuantity();
    }

    public List<T> getAllEntitiesSql(String sqlQuery) {
        return new DbServiceImpl<>(new DaoJdbc<>(
                new SessionManagerJdbc(dataSource), entityClass))
                .getBeansByQuery(sqlQuery, Collections.emptyList());
    }

    public List<T> getInRangeByRowNumber(int limit, int offset) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), entityClass))
                .getBeansInRangeByRowNumber(limit, offset);
    }

    public List<T> getInRangeByRowNumber(int limit, int offset, String sqlQuery) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), entityClass))
                .getBeansInRangeByRowNumber(limit, offset, sqlQuery);
    }

    public T getById(int id) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), entityClass))
                .getBeansById(id)
                .orElseThrow(() -> new ServiceException(EMPTY_RESULT));
    }

    public int insertEntity(T entity) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), entityClass))
                .saveBean(entity);
    }

    public int updateEntity(T entity) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), entityClass))
                .updateBean(entity);
    }

    public int updateEntity(String sqlQuery, Object value) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), entityClass))
                .updateBean(sqlQuery, Collections.singletonList(value));
    }

    public int deleteEntity(int id) {
        return new DbServiceImpl<>(new DaoJdbc<>(
                new SessionManagerJdbc(dataSource), entityClass)).deleteBean(id);
    }


    public List<T> getByStoredProc(String storedProc, List<Object> values) {
        return new DbServiceImpl<>(new DaoJdbc<>(
                new SessionManagerJdbc(dataSource), entityClass)).getBeansByCall(storedProc, values);
    }
}