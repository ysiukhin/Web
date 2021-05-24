package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;

@TableName(dbTable = "request")
public class Request implements Serializable {
    @TableColumn("id")
    private int id;
    @TableColumn("request")
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
