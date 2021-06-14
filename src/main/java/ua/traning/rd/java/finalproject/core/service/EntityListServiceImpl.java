package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

public class EntityListServiceImpl<T> implements EntityListService<T> {
    public final static Logger LOGGER = LogManager.getLogger(EntityListServiceImpl.class);
    private final Class<T> entityClass;
    private final DataSource dataSource;

    public EntityListServiceImpl(Class<T> entityClass, DataSource dataSource) {
        this.entityClass = entityClass;
        this.dataSource = dataSource;
    }

    public int totalEntityQuantity() {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(dataSource), entityClass)).beanQuantity();
    }

    public List<T> totalEntityQuantityBySql(String sqlQuery) {
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