package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;

import java.io.Serializable;

public class AcountActivityAndRequest implements Serializable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @TableColumn("kind_en")
    private String kindEn;
    @TableColumn("kind_ru")
    private String kindRu;
    @TableColumn("activity_en")
    private String activityEn;
    @TableColumn("activity_ru")
    private String activityRu;
    @TableColumn("account_activity_id")
    private int accountActivityId;
    @TableColumn("request_id")
    private int request_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKindEn() {
        return kindEn;
    }

    public void setKindEn(String kindEn) {
        this.kindEn = kindEn;
    }

    public String getKindRu() {
        return kindRu;
    }

    public void setKindRu(String kindRu) {
        this.kindRu = kindRu;
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

    public int getAccountActivityId() {
        return accountActivityId;
    }

    public void setAccountActivityId(int accountActivityId) {
        this.accountActivityId = accountActivityId;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
