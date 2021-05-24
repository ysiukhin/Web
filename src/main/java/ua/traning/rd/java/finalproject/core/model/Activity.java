package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableField;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.util.List;

@TableName(dbTable = "activity")
public class Activity implements Serializable {
    @PrimaryKey
    @TableField(dbFieldName = "id", select = 1, updatePosition = 5)
    private int id;
    @TableField(dbFieldName = "activity_en", insertPosition = 1, updatePosition = 1)
    private String activityEn;
    @TableField(dbFieldName = "activity_ru", insertPosition = 2, updatePosition = 2)
    private String activityRu;
    @TableField(dbFieldName = "status", insertPosition = 3, updatePosition = 3)
    private int status;
    @TableField(dbFieldName = "kind_id", insertPosition = 4, updatePosition = 4)
    private int kindId;
    @Linked("activity_id")
    private List<AccountActivity> accountActivities;
//    private List<Account> requests;
//

    public List<AccountActivity> getAccountActivities() {
        return accountActivities;
    }

    public void setAccountActivities(List<AccountActivity> accountActivities) {
        this.accountActivities = accountActivities;
    }
//    public List<Account> getRequests() {
//        return requests;
//    }
//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status == 1;
    }

    public void setStatus(boolean status) {
        this.status = status ? 1 : 0;
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

    public int getKindId() {
        return kindId;
    }

    public void setKindId(int kindId) {
        this.kindId = kindId;
    }
}
