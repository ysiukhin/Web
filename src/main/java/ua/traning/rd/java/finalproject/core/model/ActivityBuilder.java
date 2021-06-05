package ua.traning.rd.java.finalproject.core.model;

import java.util.ArrayList;
import java.util.List;

public class ActivityBuilder {
    private int id = 0;
    private String activityEn = "";
    private String activityRu = "";
    private int kindId = 0;
    private List<AccountActivity> activities = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();

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


    public ActivityBuilder addKindId(int kindId) {
        this.kindId = kindId;
        return this;
    }

    public ActivityBuilder addActivities(List<AccountActivity> activities) {
        this.activities = activities;
        return this;
    }

    public ActivityBuilder addRequests(List<Request> requests) {
        this.requests = requests;
        return this;
    }


    public Activity build() {
        Activity newActivity = new Activity();
        newActivity.setId(id);
        newActivity.setActivityEn(activityEn);
        newActivity.setActivityRu(activityRu);
        newActivity.setKindId(kindId);
        newActivity.setActivities(activities);
        newActivity.setRequests(requests);
        return newActivity;
    }
}
