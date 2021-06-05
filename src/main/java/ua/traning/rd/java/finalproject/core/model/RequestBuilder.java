package ua.traning.rd.java.finalproject.core.model;


public class RequestBuilder {
    int id;
    boolean request;
    private int accountId;
    private int activityId;

    public RequestBuilder addId(int id) {
        this.id = id;
        return this;
    }

    public RequestBuilder addRequest(boolean request) {
        this.request = request;
        return this;
    }

    public RequestBuilder addAccountId(int accountId) {
        this.accountId = accountId;
        return this;
    }

    public RequestBuilder addActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public Request build() {
        Request newRequest = new Request();
        newRequest.setId(id);
        newRequest.setRequest(request);
        newRequest.setAccountId(accountId);
        newRequest.setActivityId(activityId);
        return newRequest;
    }
}
