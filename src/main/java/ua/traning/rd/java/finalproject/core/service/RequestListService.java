package ua.traning.rd.java.finalproject.core.service;

import ua.traning.rd.java.finalproject.core.dao.DbServiceImpl;
import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.Activity;
import ua.traning.rd.java.finalproject.core.model.Request;
import ua.traning.rd.java.finalproject.jdbc.dao.DaoJdbc;
import ua.traning.rd.java.finalproject.jdbc.sessionmanager.SessionManagerJdbc;
import ua.traning.rd.java.finalproject.servlet.controller.Servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestListService {
    public List<AccountActivityRequestEntity> getList(int from, int to) {
        List<Request> requests = new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), Request.class))
                .getBeansInRange("id", Arrays.asList(from, to))
                .orElseThrow(() -> new ExceptionService("RequestListService got error during List<Request> obtain"));
        List<AccountActivityRequestEntity> resultList = new ArrayList<>();

        for (Request request : requests) {
            AccountActivityRequestEntity result = new AccountActivityRequestEntity();

            Account account = new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource), Account.class))
                    .getBeansById(request.getAccountId())
                    .orElseThrow(() ->
                            new ExceptionService(String.format("There is no Account with id: %d",
                                    request.getAccountId())));
            Activity activity = new DbServiceImpl<>(new DaoJdbc<>(new SessionManagerJdbc(Servlet.dataSource),
                    Activity.class)).getBeansById(request.getActivityId())
                    .orElseThrow(() ->
                            new ExceptionService(String.format("There is no Activity with id: %d",
                                    request.getActivityId())));
            result.setRequest(request);
            result.setAccount(account);
            result.setActivity(activity);
            resultList.add(result);
        }
        return resultList;
    }
}
