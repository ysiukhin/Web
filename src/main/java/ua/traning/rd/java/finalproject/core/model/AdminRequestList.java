package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;

import java.io.Serializable;

public class AdminRequestList implements Serializable {
    @PrimaryKey
    @TableColumn("account_id")
    private int accountId;
    @TableColumn("first_name")
    private String firstName;
    @TableColumn("last_name")
    private String lastName;
    @TableColumn("email")
    private String email;
    @TableColumn("request_id")
    private int requestId;
    @TableColumn("request")
    private int request;
    @TableColumn("activity_id")
    private int activityId;
    @TableColumn("activity_en")
    private String activityEn;
    @TableColumn("activity_ru")
    private String activityRu;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityEn() {
        return activityEn;
    }

    public void setActivityEn(String activityEn) {
        this.activityEn = activityEn;
    }

    public String getActivityRu() {
        return activityRu;
    }

    public void setActivityRu(String activityRu) {
        this.activityRu = activityRu;
    }
}
