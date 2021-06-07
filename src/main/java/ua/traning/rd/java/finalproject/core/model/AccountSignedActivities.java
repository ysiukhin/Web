package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;

import java.io.Serializable;
import java.sql.Timestamp;

public class AccountSignedActivities implements Serializable {
    @PrimaryKey
    @TableColumn("account_activity_id")
    private int accountActivityId;
    @TableColumn("kind_en")
    private String kindEn;
    @TableColumn("kind_ru")
    private String kindRu;
    @TableColumn("activity_en")
    private String activityEn;
    @TableColumn("activity_ru")
    private String activityRu;
    @TableColumn("start")
    private Timestamp start;
    @TableColumn("record_id")
    private long recordId;

    public int getAccountActivityId() {
        return accountActivityId;
    }

    public void setAccountActivityId(int accountActivityId) {
        this.accountActivityId = accountActivityId;
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

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }
}
