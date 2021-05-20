package com.epam.rd.java.finalproject.core.model;

import com.epam.rd.java.finalproject.core.annotation.TableField;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
    @TableField(value = "id", select = 1, updatePosition = 4)
    private int id;
    @TableField(value = "project_name", insertPosition = 1, updatePosition = 1)
    private String projectName;
    @TableField(value = "project_desk", insertPosition = 2, updatePosition = 2)
    private String projectDesc;
    @TableField(value = "status", insertPosition = 3, updatePosition = 3)
    private boolean status;
    private List<Account> accounts;
    private List<Task> tasks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
