package ua.traning.rd.java.finalproject.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.traning.rd.java.finalproject.core.dao.Dao;
import ua.traning.rd.java.finalproject.core.dao.DbServiceException;
import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.AccountActivity;
import ua.traning.rd.java.finalproject.core.model.Activity;
import ua.traning.rd.java.finalproject.core.model.Request;
import ua.traning.rd.java.finalproject.core.sessionmanager.SessionManager;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;
import ua.traning.rd.java.finalproject.servlet.exception.ServiceException;

import java.util.*;
import java.util.stream.Collectors;

public class RequestListService {
    public final static Logger LOGGER = LogManager.getLogger(RequestListService.class);
//    public List<AccountActivityRequestEntity> getList(int from, int to) {
//        List<Request> requests = getRequests(from, to);
//        Map<Integer, Account> accounts = getRequestsAccounts(requests).stream()
//                .collect(Collectors.toMap(Account::getId, list -> list));
//        Map<Integer, Activity> activities = getRequestsActivities(requests).stream()
//                .collect(Collectors.toMap(Activity::getId, list -> list));
//
//        return requests.stream().map(request -> new AccountActivityRequestEntity(
//                accounts.get(request.getAccountId()),
//                activities.get(request.getActivityId()),
//                request))
//                .collect(Collectors.toList());
//    }
//
// TRANSACTION
public boolean processRequest(boolean isNewAccountActivity, int accountId, int activityId, int requestId) {
    try (SessionManager sessionManager = new SessionManagerJdbc(Servlet.dataSource)) {
        Dao<AccountActivity> accountActivityDao =
                new DaoJdbc<>(sessionManager, AccountActivity.class);
        Dao<Request> requestDao =
                new DaoJdbc<>(sessionManager, Request.class);
        sessionManager.beginSession();
        try {
            if (isNewAccountActivity) {
                    AccountActivity newAccountActivity = new AccountActivity();
                    newAccountActivity.setAccountId(accountId);
                    newAccountActivity.setActivityId(activityId);
                    accountActivityDao.insert(newAccountActivity);
                    requestDao.delete(requestId);
                } else {
                    accountActivityDao.delete(Arrays.asList("account_id", "activity_id")
                            , Arrays.asList(accountId, activityId));
                    requestDao.delete(requestId);
                }
                sessionManager.commitSession();
                return true;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    private List<Activity> getRequestsActivities(List<Request> requests) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), Activity.class))
                .getBeansFromList("id", requests.stream().map(Request::getActivityId).collect(Collectors.toList()))
                .orElseThrow(() -> new ServiceException("There is no activities associated with request"));
    }

    private List<Account> getRequestsAccounts(List<Request> requests) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), Account.class))
                .getBeansFromList("id", requests.stream().map(Request::getAccountId).collect(Collectors.toList()))
                .orElseThrow(() -> new ServiceException("There is no accounts associated with request"));
    }

    private List<Request> getRequests(int limit, int offset) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), Request.class))
                .getBeansInRangeByRowNumber(limit, offset)
                .orElseThrow(() -> new ServiceException("RequestListService got error during List<Request> obtain"));
    }
}
