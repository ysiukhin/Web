package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;

import java.io.Serializable;

public class AdminActivityList implements Serializable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @TableColumn("activity_en")
    private String activityEn;
    @TableColumn("activity_ru")
    private String activityRu;
    @TableColumn("kind_ru")
    private String kindRu;
    @TableColumn("kind_en")
    private String kindEn;
    @TableColumn("kind_id")
    private int kindId;

//    public AdminActivityList() {}
//
//    private AdminActivityList(Builder builder) {
//        this.id = builder.id;
//        this.activityEn = builder.activityEn;
//        this.activityRu = builder.activityRu;
//        this.kindEn = builder.kindEn;
//        this.kindRu = builder.kindRu;
//        this.kindId = builder.kindId;
//    }

    public String getKindRu() {
        return kindRu;
    }

    public void setKindRu(String kindRu) {
        this.kindRu = kindRu;
    }

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    public String getKindEn() {
        return kindEn;
    }

    public void setKindEn(String kindEn) {
        this.kindEn = kindEn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

//    public static class Builder {
//        private int id;
//        private String activityEn;
//        private String activityRu;
//        private String kindRu;
//        private String kindEn;
//        private int kindId;
//
//        Builder addId(int id) {
//            this.id = id;
//            return this;
//        }
//        Builder addActivityEn(String activityEn) {
//            this.activityEn = activityEn;
//            return this;
//        }
//        Builder addActivityRu(String activityRu) {
//            this.activityRu = activityRu;
//            return this;
//        }
//        Builder addKindEn(String kindEn) {
//            this.kindEn = kindEn;
//            return this;
//        }
//        Builder addKindRu(String kindRu) {
//            this.kindRu = kindRu;
//            return this;
//        }
//        Builder addKindRu(int kindId) {
//            this.kindId = kindId;
//            return this;
//        }
//        AdminActivityList build() {
//            return new AdminActivityList(this);
//        }
//    }
}
