package com.epam.rd.java.finalproject.core.model;

import com.epam.rd.java.finalproject.core.annotation.TableField;

import java.io.Serializable;

public class Account implements Serializable {
    @TableField(dbFieldName = "id", select = 1, updatePosition = 9)
    private int id;
    @TableField(dbFieldName = "first_name", insertPosition = 1, updatePosition = 1)
    private String firstName;
    @TableField(dbFieldName = "last_name", insertPosition = 2, updatePosition = 2)
    private String lastName;
    @TableField(dbFieldName = "middle_name", insertPosition = 3, updatePosition = 3)
    private String middleName;
    @TableField(dbFieldName = "email", insertPosition = 4, updatePosition = 4)
    private String email;
    @TableField(dbFieldName = "login", insertPosition = 5, updatePosition = 5)
    private String login;
    @TableField(dbFieldName = "password", insertPosition = 6, updatePosition = 6)
    private String md5;
    @TableField(dbFieldName = "status", insertPosition = 7, updatePosition = 7)
    private boolean status;
    @TableField(dbFieldName = "roleId", insertPosition = 8, updatePosition = 8)
    private int roleId;
    private Role role;

    public boolean isStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", md5='" + md5 + '\'' +
                ", status=" + status +
                ", roleId=" + roleId +
                '}';
    }
}
