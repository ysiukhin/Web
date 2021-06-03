package ua.traning.rd.java.finalproject.core.service;

import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.Activity;
import ua.traning.rd.java.finalproject.core.model.Request;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;

import java.util.*;
import java.util.stream.Collectors;

public class RequestListService {
    public List<AccountActivityRequestEntity> getList(int from, int to) {
        List<Request> requests = getRequests(from, to);
        Map<Integer, Account> accounts = getRequestsAccounts(requests).stream()
                .collect(Collectors.toMap(Account::getId, list -> list));
        Map<Integer, Activity> activities = getRequestsActivities(requests).stream()
                .collect(Collectors.toMap(Activity::getId, list -> list));

        return requests.stream().map(request -> new AccountActivityRequestEntity(
                accounts.get(request.getAccountId()),
                activities.get(request.getActivityId()),
                request))
                .collect(Collectors.toList());
    }

    private List<Activity> getRequestsActivities(List<Request> requests) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), Activity.class))
                .getBeansFromList("id", requests.stream().map(Request::getActivityId).collect(Collectors.toList()))
                .orElseThrow(() -> new ExceptionService("There is no activities associated with request"));
    }

    private List<Account> getRequestsAccounts(List<Request> requests) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), Account.class))
                .getBeansFromList("id", requests.stream().map(Request::getAccountId).collect(Collectors.toList()))
                .orElseThrow(() -> new ExceptionService("There is no accounts associated with request"));
    }

    private List<Request> getRequests(int from, int to) {
        return new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), Request.class))
                .getBeansInRange("id", Arrays.asList(from, to))
                .orElseThrow(() -> new ExceptionService("RequestListService got error during List<Request> obtain"));
    }
}
