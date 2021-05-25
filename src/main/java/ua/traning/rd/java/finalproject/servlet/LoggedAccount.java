package ua.traning.rd.java.finalproject.servlet;

import ua.traning.rd.java.finalproject.core.model.Account;

import java.util.Objects;

public class LoggedAccount {
    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }

    private ROLE role;
    private Account account;

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
