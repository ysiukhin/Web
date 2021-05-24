package ua.traning.rd.java.finalproject.core.model;

import ua.traning.rd.java.finalproject.core.annotation.Linked;
import ua.traning.rd.java.finalproject.core.annotation.PrimaryKey;
import ua.traning.rd.java.finalproject.core.annotation.TableField;
import ua.traning.rd.java.finalproject.core.annotation.TableName;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

@TableName(dbTable = "account")
public class Account implements Serializable {
    @PrimaryKey
    @TableField(dbFieldName = "id", select = 1, updatePosition = 7)
    private int id;
    @TableField(dbFieldName = "first_name", insertPosition = 1, updatePosition = 1)
    private String firstName;
    @TableField(dbFieldName = "last_name", insertPosition = 2, updatePosition = 2)
    private String lastName;
    @TableField(dbFieldName = "middle_name", insertPosition = 3, updatePosition = 3)
    private String middleName;
    @TableField(dbFieldName = "email", insertPosition = 4, updatePosition = 4)
    private String email;
    @TableField(dbFieldName = "md5", insertPosition = 5, updatePosition = 5)
    private String md5;
    @TableField(dbFieldName = "status", insertPosition = 6, updatePosition = 6)
    private int status;
    @Linked("account_id")
    private List<AccountActivity> accountActivities;
//    @Linked(className = "Requests")
//    private List<Requests> requests;


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
