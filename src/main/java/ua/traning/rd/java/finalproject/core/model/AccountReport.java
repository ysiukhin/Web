package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;

import java.math.BigDecimal;

public class AccountReport {
    @PrimaryKey
    @TableColumn("id")
    int id;
    @TableColumn("first_name")
    private String firstName;
    @TableColumn("last_name")
    private String lastName;
    @TableColumn("activity_count")
    private long activityCount;
    @TableColumn("total_time_In_minutes")
    private BigDecimal totalTimeInMinutes;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setActivityCount(long activityCount) {
        this.activityCount = activityCount;
    }

    public void setTotalTimeInMinutes(BigDecimal totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getActivityCount() {
        return activityCount;
    }

    public BigDecimal getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }
}
