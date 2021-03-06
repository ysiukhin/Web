package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;

public class ActivityReport {
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
    @TableColumn("account_count")
    private Long accountCount;

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

    public Long getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(Long accountCount) {
        this.accountCount = accountCount;
    }
}
