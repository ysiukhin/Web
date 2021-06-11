package ua.traning.rd.java.finalproject.core.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static ua.traning.rd.java.finalproject.Constants.ID;

public class DbServiceImpl<T> implements DbService<T> {
    private final static Logger LOGGER = LogManager.getLogger(DbServiceImpl.class);

    private final Dao<T> dao;

    public DbServiceImpl(Dao<T> dao) {
        this.dao = dao;
    }

    public Optional<T> getBeansById(int id) {
        return Optional.ofNullable(doService(() -> dao.selectBy(Collections.singletonList(ID),
                Collections.singletonList(id)).get(0)));
    }

    public List<T> getBeansInRangeByRowNumber(int limit, int offset) {
        return doService(() -> dao.selectByRecordNumberInRange(limit, offset));
    }

    public List<T> getBeansInRangeByRowNumber(int limit, int offset, String sqlQuery) {
        return doService(() -> dao.selectByRecordNumberInRange(limit, offset, sqlQuery));
    }

    public List<T> getBeansByQuery(String sqlQuery, List<Object> values) {
        return doService(() -> dao.selectBy(sqlQuery, values));
    }

    public List<T> getBeansByCall(String procName, List<Object> values) {
        return doService(() -> dao.call(procName, values));
    }

    public List<T> getBeansBy(String columnName, Object value) {
        return doService(() ->
                dao.selectBy(Collections.singletonList(columnName),
                        Collections.singletonList(value)));
    }

    public int saveBean(T bean) {
        return doService(() -> dao.insert(bean));
    }

    public int updateBean(T bean) {
        return doService(() -> dao.update(bean));
    }

    public int updateBean(String sqlQuery, List<Object> values) {
        return doService(() -> dao.update(sqlQuery, values));
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
//            try {
            U result = service.get();
            sessionManager.commitSession();
            return result;
//            } catch (Exception e) {
//                LOGGER.error(e.getMessage(), e);
//                sessionManager.rollbackSession();
//                throw new DbServiceException(e);
//            }
        }
    }
}