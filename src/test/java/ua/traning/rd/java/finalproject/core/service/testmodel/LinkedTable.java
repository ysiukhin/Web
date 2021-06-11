package ua.traning.rd.java.finalproject.core.service.testmodel;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;


@TableName(dbTable = "linked_table")
public class LinkedTable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @TableColumn("primary_table_id")
    private int primaryTableId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrimaryTableId() {
        return primaryTableId;
    }

    public void setPrimaryTableId(int primaryTableId) {
        this.primaryTableId = primaryTableId;
    }
}
