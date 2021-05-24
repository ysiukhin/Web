package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableField;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

@TableName(dbTable = "account_activity")
public class AccountActivity implements Serializable {
    @PrimaryKey
    @TableField(dbFieldName = "id", select = 1, updatePosition = 4)
    private int id;
    @TableField(dbFieldName = "status", insertPosition = 1, updatePosition = 1)
    private int status;
    @TableField(dbFieldName = "account_id", insertPosition = 2, updatePosition = 2)
    private int accountId;
    @TableField(dbFieldName = "activity_id", insertPosition = 3, updatePosition = 3)
    private int activityId;
    @Linked("account_activity_id")
    private List<Record> records;

    public int getId() {
        return id;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountActivity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("status=" + status)
                .add("accountId=" + accountId)
                .add("activityId=" + activityId)
                .add("records=" + records)
                .toString();
    }
}
