package ua.traning.rd.java.finalproject.core.service.testmodel;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.util.List;

@TableName(dbTable = "primary_table")
public class PrimaryTable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @Linked(value = "primary_table_id", strategy = "active")
    private List<LinkedTable> linkedTableList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LinkedTable> getLinkedTableList() {
        return linkedTableList;
    }

    public void setLinkedTableList(List<LinkedTable> linkedTableList) {
        this.linkedTableList = linkedTableList;
    }
}

