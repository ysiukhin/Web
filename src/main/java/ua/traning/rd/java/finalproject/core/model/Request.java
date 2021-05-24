package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.TableField;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;

@TableName(dbTable = "request")
public class Request implements Serializable {
    @TableField(dbFieldName = "id", select = 1, updatePosition = 2)
    private int id;
    @TableField(dbFieldName = "request", insertPosition = 1, updatePosition = 1)
    private int request;

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
}
