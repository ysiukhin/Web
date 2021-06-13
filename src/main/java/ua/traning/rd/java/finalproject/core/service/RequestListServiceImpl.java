package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.core.model.AccountActivity;
import ua.traning.rd.java.finalproject.core.model.Request;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.exception.DaoException;


import javax.sql.DataSource;
import java.util.*;

import static ua.traning.rd.java.finalproject.Constants.ACCOUNT_ID;
import static ua.traning.rd.java.finalproject.Constants.ACTIVITY_ID;


public class RequestListServiceImpl implements RequestListService {
    public final static Logger LOGGER = LogManager.getLogger(RequestListServiceImpl.class);
    private final SessionManager sessionManager;// = new SessionManagerJdbc(dataSource);
    private final Dao<AccountActivity> accountActivityDao;// = new DaoJdbc<>(sessionManager, AccountActivity.class);
    private final Dao<Request> requestDao;// = new DaoJdbc<>(sessionManager, Request.class);

    public RequestListServiceImpl(DataSource dataSource) {
        this.sessionManager = new SessionManagerJdbc(dataSource);
        this.accountActivityDao = new DaoJdbc<>(sessionManager, AccountActivity.class);
        this.requestDao = new DaoJdbc<>(sessionManager, Request.class);
    }

    public RequestListServiceImpl(DataSource dataSource,
                                  SessionManager sessionManager,
                                  Dao<AccountActivity> accountActivityDao,
                                  Dao<Request> requestDao) {
        this.sessionManager = sessionManager;
        this.accountActivityDao = accountActivityDao;
        this.requestDao = requestDao;
    }


    // TRANSACTION
    public boolean processRequestTransaction(boolean isNewAccountActivity, int accountId, int activityId, int requestId) {
        sessionManager.beginSession();
        try {
            if (isNewAccountActivity) {
                AccountActivity newAccountActivity = new AccountActivity();
                newAccountActivity.setAccountId(accountId);
                newAccountActivity.setActivityId(activityId);
                accountActivityDao.insert(newAccountActivity);
            } else {
                accountActivityDao.delete(Arrays.asList(ACCOUNT_ID, ACTIVITY_ID)
                        , Arrays.asList(accountId, activityId));
            }
            requestDao.delete(requestId);
            sessionManager.commitSession();
            return true;
        } catch (DaoException exception) {
            LOGGER.error(exception.getMessage(), exception);
            sessionManager.rollbackSession();
            throw exception;
        }
    }

    @Override
    public void close() {
        sessionManager.close();
    }
}
