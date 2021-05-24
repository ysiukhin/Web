package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableField;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.time.Instant;

@TableName(dbTable = "record")
public class Record implements Serializable {
    @PrimaryKey
    @TableField(dbFieldName = "id", select = 1, updatePosition = 4)
    private int id;
    @TableField(dbFieldName = "start", insertPosition = 1, updatePosition = 1)
    private Instant start;
    @TableField(dbFieldName = "end", insertPosition = 2, updatePosition = 2)
    private Instant end;
    @TableField(dbFieldName = "account_activity_id", insertPosition = 3, updatePosition = 3)
    private int accountActivityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public int getAccountActivityId() {
        return accountActivityId;
    }

    public void setAccountActivityId(int accountActivityId) {
        this.accountActivityId = accountActivityId;
    }
}
