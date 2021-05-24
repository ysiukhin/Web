package ua.traning.rd.java.finalproject.core.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.function.Supplier;

public class DbServiceImpl<T> implements DbService<T> {
    private final static Logger LOGGER = LogManager.getLogger(DbServiceImpl.class);

    private final Dao<T> dao;

    public DbServiceImpl(Dao<T> dao) {
        this.dao = dao;
    }

    public List<T> getAllBeans() {
        return doService(dao::select);
    }

    public T getBeansById(int id) {
        return doService(() -> dao.selectBy("id", id).get(0));
    }

    public int saveBean(T bean) {
        return doService(() -> dao.insert(bean));
    }

    public int updateBean(T bean) {
        return doService(() -> dao.update(bean));
    }

    protected <U> U doService(Supplier<U> job) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                U result = job.get();
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
