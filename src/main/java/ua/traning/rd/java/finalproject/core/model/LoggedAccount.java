package ua.traning.rd.java.finalproject.core.model;

import java.util.Objects;
import java.util.StringJoiner;

public class LoggedAccount {
    @Override
    public String toString() {
        return new StringJoiner(", ", LoggedAccount.class.getSimpleName() + "[", "]")
                .add("sessionId='" + sessionId + "'")
                .add("role=" + role)
                .add("account=" + account)
                .toString();
    }

    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }

    private ROLE role;
    private Account account;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String sessionId;

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoggedAccount)) return false;
        LoggedAccount that = (LoggedAccount) o;
        return getRole() == that.getRole() && getAccount().getId() == that.getAccount().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRole(), getAccount());
    }
}
