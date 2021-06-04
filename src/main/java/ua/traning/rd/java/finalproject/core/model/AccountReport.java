package ua.traning.rd.java.finalproject.core.model;

public class AccountReport {
    int id;
    private String firstName;
    private String lastName;
    private String email;
    private int activityCount;
    private int totalTimeInMinutes;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public void setTotalTimeInMinutes(int totalTimeInMinutes) {
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

    public String getEmail() {
        return email;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public int getTotalTimeInMinutes() {
        return totalTimeInMinutes;
    }
}
