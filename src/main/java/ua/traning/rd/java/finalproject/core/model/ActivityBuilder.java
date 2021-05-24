package ua.traning.rd.java.finalproject.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityBuilder {
    private int id = 0;
    private String activityEn = "";
    private String activityRu = "";
    private boolean status = false;
    private int kindId = 0;
    private List<AccountActivity> accountActivities = new ArrayList<>();
//    private List<Account> requests = new ArrayList<>();

    public ActivityBuilder addId(int id) {
        this.id = id;
        return this;
    }

    public ActivityBuilder addActivityEn(String activityEn) {
        this.activityEn = activityEn;
        return this;
    }

    public ActivityBuilder addActivityRu(String activityRu) {
        this.activityRu = activityRu;
        return this;
    }

    public ActivityBuilder addStatus(boolean status) {
        this.status = status;
        return this;
    }

    public ActivityBuilder addKindId(int kindId) {
        this.kindId = kindId;
        return this;
    }

    public ActivityBuilder addAAccountActivity(List<AccountActivity> accountActivities) {
        this.accountActivities = accountActivities;
        return this;
    }

//    public ActivityBuilder addRequests(List<Account> requests) {
//        this.requests = requests;
//        return this;
//    }


    public Activity build() {
        Activity newActivity = new Activity();
        newActivity.setId(id);
        newActivity.setActivityEn(activityEn);
        newActivity.setActivityRu(activityRu);
        newActivity.setStatus(status);
        newActivity.setKindId(kindId);
        newActivity.setAccountActivities(accountActivities);
//        newActivity.setRequests(requests);
        return newActivity;
    }
}
