package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.util.List;

@TableName(dbTable = "activity")
public class Activity implements Serializable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @TableColumn("activity_en")
    private String activityEn;
    @TableColumn("activity_ru")
    private String activityRu;
    @TableColumn("status")
    private int status;
    @TableColumn("kind_id")
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
