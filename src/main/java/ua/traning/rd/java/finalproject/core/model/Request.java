package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.util.Optional;

@TableName(dbTable = "request")
public class Request implements Serializable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @TableColumn("request")
    private int request;
    @TableColumn("account_id")
    private int accountId;
    @TableColumn("activity_id")
    private int activityId;
    @TableColumn("status")
    private Integer status;

    public Optional<Integer> getStatus() {
        return Optional.ofNullable(status);
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getRequest() {
        return request == 1;
    }

    public void setRequest(Boolean request) {
        this.request = request ? 1 : 0;
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

//    public enum Request { SIGN_OUT, SIGN_IN }
//    public enum dbStatus {  CONSIDERATION(null), REJECTED(0), APPROVED(1);
//        private Integer dbStatus;
//        dbStatus(Integer dbStatus) {
//            this.dbStatus = dbStatus;
//        }
//        public Integer getStatus() {
//            return dbStatus;
//        }
//    }
}
