package ua.traning.rd.java.finalproject.core.service;

import ua.traning.rd.java.finalproject.core.model.Account;
import ua.traning.rd.java.finalproject.core.model.Activity;
import ua.traning.rd.java.finalproject.core.model.Request;

public class AccountActivityRequestEntity {
    private Account account;
    private Activity activity;
    private Request request;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
