package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.core.dao.DbService;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EntityListService<T> {
    public final static Logger LOGGER = LogManager.getLogger(EntityListService.class);
    private final Class<T> entityClass;

    public EntityListService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public int totalQuantity() {
        LOGGER.info("IN EntityService --> totalQuantity()");
        List<T> entityList = getAllEntities();
        LOGGER.info("IN EntityService --> totalQuantity()");
        return entityList.size();
    }

    public List<T> getAllEntities() {
        LOGGER.info("IN EntityService --> getAllAccounts()");
        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(Servlet.dataSource);
        Dao<T> entityDao = new DaoJdbc<>(sessionManagerJdbc, entityClass);
        DbService<T> dbServiceAccount = new DbServiceImpl<>(entityDao);
        Optional<List<T>> accounts = dbServiceAccount.getAllBeans();
        LOGGER.info("IN AccountService --> getAllAccounts()");
        return accounts.orElseThrow(() -> new ExceptionService("There is no any Accounts in database."));
    }

    public List<T> getInRange(int from, int to) {
        LOGGER.info("IN EntityService --> getInRange()");
        SessionManagerJdbc sessionManagerJdbc = new SessionManagerJdbc(Servlet.dataSource);
        Dao<T> accountDao =
                new DaoJdbc<>(sessionManagerJdbc, entityClass);
        DbService<T> dbServiceAccount = new DbServiceImpl<>(accountDao);
        Optional<List<T>> accounts = dbServiceAccount
                .getBeansInRange("id", Arrays.asList(from, to));
        LOGGER.info("IN EntityService --> getInRange()");
        return accounts.orElseThrow(() -> new ExceptionService("There is no any Accounts in database."));
    }
}