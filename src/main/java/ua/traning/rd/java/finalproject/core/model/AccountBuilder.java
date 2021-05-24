package ua.traning.rd.java.finalproject.core.model;

import java.util.ArrayList;
import java.util.List;

public class AccountBuilder {
    private int id = 0;
    private String firstName = "";
    private String lastName = "";
    private String middleName = "";
    private String email = "";
    private String md5 = "";
    private boolean status = false;
    private List<AccountActivity> accountActivities = new ArrayList<>();
//    private List<Activity> requests = new ArrayList<>();

    public AccountBuilder addId(int id) {
        this.id = id;
        return this;
    }

    public AccountBuilder addFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountBuilder addLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AccountBuilder addMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public AccountBuilder addEmail(String email) {
        this.email = email;
        return this;
    }

    public AccountBuilder addMd5(String md5) {
        this.md5 = md5;
        return this;
    }

    public AccountBuilder addStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public AccountBuilder addAccountActivity(List<AccountActivity> accountActivities) {
        this.accountActivities = accountActivities;
        return this;
    }

//    public AccountBuilder addRequests(List<Activity> requests) {
//        this.requests = requests;
//        return this;
//    }

    public Account build() {
        Account newAccount = new Account();
        newAccount.setId(id);
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setMiddleName(middleName);
        newAccount.setEmail(email);
        newAccount.setMd5(md5);
        newAccount.setStatus(status);
        newAccount.setAccountActivities(accountActivities);
//        newAccount.setRequests(requests);
        return newAccount;
    }
}
