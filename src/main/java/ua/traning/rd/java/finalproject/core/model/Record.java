package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@TableName(dbTable = "record")
public class Record implements Serializable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @TableColumn("start")
    private Timestamp start;
    @TableColumn("end")
    private Timestamp end;
    @TableColumn("account_activity_id")
    private int accountActivityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getAccountActivityId() {
        return accountActivityId;
    }

    public void setAccountActivityId(int accountActivityId) {
        this.accountActivityId = accountActivityId;
    }
}
