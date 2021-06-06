package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import java.util.List;

public class EntityListService<T> {
    public final static Logger LOGGER = LogManager.getLogger(EntityListService.class);
    private final Class<T> entityClass;

    public EntityListService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public int totalQuantity() {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), entityClass)).beanQuantity();
    }

    public List<T> getAllEntities() {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), entityClass)).getAllBeans()
                .orElseThrow(() -> new ServiceException("There is no any Entities in database."));
    }

    public List<T> getInRangeByRowNumber(int limit, int offset) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), entityClass))
                .getBeansInRangeByRowNumber(limit, offset)
                .orElseThrow(() -> new ServiceException("There is no any Accounts in database."));
    }

    public List<T> getInRangeByRowNumber(int limit, int offset, String sqlQuery) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), entityClass))
                .getBeansInRangeByRowNumber(limit, offset, sqlQuery)
                .orElseThrow(() -> new ServiceException("There is no any Accounts in database."));
    }

    public T getById(int id) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), entityClass))
                .getBeansById(id)
                .orElseThrow(() -> new ServiceException("There is no any Accounts in database."));
    }

    public List<T> getIdByColumn(String columnName, Object value) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), entityClass))
                .getBeansBy(columnName, value)
                .orElseThrow(() -> new ServiceException("There is no any Entities in database."));
    }

    public int insertEntity(T entity) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), entityClass))
                .saveBean(entity);
    }

    public int updateEntity(T entity) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), entityClass))
                .updateBean(entity);
    }

    public int deleteEntity(int id) {
        return new DbServiceImpl<>(new DaoJdbc<>(
                new SessionManagerJdbc(Servlet.dataSource), entityClass)).deleteBean(id);
    }

    public int totalEntities() {
        return new DbServiceImpl<>(new DaoJdbc<>(
                new SessionManagerJdbc(Servlet.dataSource), entityClass)).beanQuantity();
    }

    public List<T> getByStoredProc(String storedProc, int id) {
        return new DbServiceImpl<>(new DaoJdbc<>(
                new SessionManagerJdbc(Servlet.dataSource), entityClass)).getBeansByCall(storedProc, id)
                .orElseThrow(() -> new ServiceException("There is no any Entities in database."));
    }
}