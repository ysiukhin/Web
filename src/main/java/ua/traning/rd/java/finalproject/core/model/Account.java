package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableColumn;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

@TableName(dbTable = "account")
public class Account implements Serializable {
    @PrimaryKey
    @TableColumn("id")
    private int id;
    @TableColumn("first_name")
    private String firstName;
    @TableColumn("last_name")
    private String lastName;
    @TableColumn("middle_name")
    private String middleName;
    @TableColumn("email")
    private String email;
    @TableColumn("md5")
    private String md5;
    @TableColumn("status")
    private int status;
    @Linked("account_id")
    private List<AccountActivity> accountActivities;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean getStatus() {
        return status == 1;
    }

    public void setStatus(boolean status) {
        this.status = status ? 1 : 0;
    }

    public List<AccountActivity> getAccountActivities() {
        return accountActivities;
    }

    public void setAccountActivities(List<AccountActivity> accountActivities) {
        this.accountActivities = accountActivities;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("middleName='" + middleName + "'")
                .add("email='" + email + "'")
                .add("md5='" + md5 + "'")
                .add("status=" + status)
                .add("accountActivities=" + accountActivities)
                .toString();
    }
}
