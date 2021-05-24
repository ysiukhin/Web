package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.util.List;

@TableName(dbTable = "kind")
public class Kind implements Serializable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @TableColumn("kind_ru")
    private String kindRu;
    @TableColumn("kind_en")
    private String kindEn;
    @Linked("kind_id")
    private List<Activity> activities;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKindRu() {
        return kindRu;
    }

    public void setKindRu(String kindRu) {
        this.kindRu = kindRu;
    }

    public String getKindEn() {
        return kindEn;
    }

    public void setKindEn(String kindEn) {
        this.kindEn = kindEn;
    }
}
