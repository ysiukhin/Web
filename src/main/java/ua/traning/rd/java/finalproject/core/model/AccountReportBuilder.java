package ua.traning.rd.java.finalproject.core.model;


public class AccountReportBuilder {
    int id;
    private String firstName;
    private String lastName;
    private String email;
    private int activityCount;
    private int totalTimeInMinutes;


    public AccountReportBuilder addId(int id) {
        this.id = id;
        return this;
    }

    public AccountReportBuilder addFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountReportBuilder addLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public AccountReportBuilder addEmail(String email) {
        this.email = email;
        return this;
    }


    public AccountReportBuilder addActivityCount(int activityCount) {
        this.activityCount = activityCount;
        return this;
    }

    public AccountReportBuilder addTotalTimeInMinutes(int totalTimeInMinutes) {
        this.totalTimeInMinutes = totalTimeInMinutes;
        return this;
    }


    public AccountReport build() {
        AccountReport accountReport = new AccountReport();
        accountReport.setId(id);
        accountReport.setFirstName(firstName);
        accountReport.setLastName(lastName);
        accountReport.setEmail(email);
        accountReport.setActivityCount(activityCount);
        accountReport.setTotalTimeInMinutes(totalTimeInMinutes);
        return accountReport;
    }
}
