package ua.traning.rd.java.finalproject.core.service;

public interface RequestListService extends AutoCloseable {
    boolean processRequestTransaction(boolean isNewAccountActivity, int accountId, int activityId, int requestId);
}
