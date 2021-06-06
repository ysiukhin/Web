package ua.traning.rd.java.finalproject.core.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class DbServiceImpl<T> implements DbService<T> {
    private final static Logger LOGGER = LogManager.getLogger(DbServiceImpl.class);

    private final Dao<T> dao;

    public DbServiceImpl(Dao<T> dao) {
        this.dao = dao;
    }

    public Optional<List<T>> getAllBeans() {
        return Optional.ofNullable(doService(dao::select));
    }

    public Optional<T> getBeansById(int id) {
        return Optional.ofNullable(doService(() -> dao.selectBy(Collections.singletonList("id"),
                Collections.singletonList(id)).get(0)));
    }

    public Optional<List<T>> getBeansInRangeByRowNumber(int limit, int offset) {
        return Optional.ofNullable(doService(() -> dao.selectByRecordNumberInRange(limit, offset)));
    }

    public Optional<List<T>> getBeansInRangeByRowNumber(int limit, int offset, String sqlQuery) {
        return Optional.ofNullable(doService(() -> dao.selectByRecordNumberInRange(limit, offset, sqlQuery)));
    }

    public Optional<List<T>> getBeansFromList(String columnName, List<Object> fields) {
        return Optional.ofNullable(doService(() -> dao.selectByFromList(columnName, fields)));
    }

    public Optional<List<T>> getBeansBy(List<String> columnNames, List<Object> values) {
        return Optional.ofNullable(doService(() -> dao.selectBy(columnNames, values)));
    }

    public Optional<List<T>> getBeansByCall(String procName, List<Object> values) {
        return Optional.ofNullable(doService(() -> dao.call(procName, values)));
    }

    public Optional<List<T>> getBeansByCall(String procName, Object value) {
        return Optional.ofNullable(doService(() -> dao.call(procName,
                Collections.singletonList(value))));
    }

    public Optional<List<T>> getBeansBy(String columnName, Object value) {
        return Optional.ofNullable(doService(() ->
                dao.selectBy(Collections.singletonList(columnName),
                        Collections.singletonList(value))));
    }

    public int saveBean(T bean) {
        return doService(() -> dao.insert(bean));
    }

    public int updateBean(T bean) {
        return doService(() -> dao.update(bean));
    }

    public int beanQuantity() {
        return doService(dao::size);
    }

    public int deleteBean(int id) {
        return doService(() -> dao.delete(id));
    }

    private <U> U doService(Supplier<U> service) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                U result = service.get();
                sessionManager.commitSession();
//                logger.debug("Account: {}", allAccounts);
                return result;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }
}