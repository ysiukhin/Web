package ua.traning.rd.java.finalproject.core.model;

public class ActivityReportBuilder {
    int id;
    String kindEn;
    String kindRu;
    String activityEn;
    String activityRu;
    int accountCount;
    int requestCount;


    public ActivityReportBuilder addId(int rowNumber) {
        this.id = rowNumber;
        return this;
    }

    public ActivityReportBuilder addKindEn(String kindEn) {
        this.kindEn = kindEn;
        return this;
    }

    public ActivityReportBuilder addKindRu(String kindRu) {
        this.kindRu = kindRu;
        return this;
    }

    public ActivityReportBuilder addActivityEn(String activityEn) {
        this.activityEn = activityEn;
        return this;
    }

    public ActivityReportBuilder addActivityRu(String activityRu) {
        this.activityRu = activityRu;
        return this;
    }

    public ActivityReportBuilder addAccountCount(int accountCount) {
        this.accountCount = accountCount;
        return this;
    }

    public ActivityReportBuilder addRequestCount(int requestCount) {
        this.requestCount = requestCount;
        return this;
    }

    public ActivityReport build() {
        ActivityReport newReport = new ActivityReport();
        newReport.setId(id);
        newReport.setKindEn(kindEn);
        newReport.setKindRu(kindRu);
        newReport.setActivityEn(activityEn);
        newReport.setActivityRu(activityRu);
        newReport.setAccountCount(accountCount);
        newReport.setRequestCount(requestCount);
        return newReport;
    }
}
