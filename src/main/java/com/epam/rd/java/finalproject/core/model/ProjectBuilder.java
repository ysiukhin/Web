package com.epam.rd.java.finalproject.core.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectBuilder {
    private int id;
    private String projectName;
    private String projectDesc;
    private boolean status;
    private List<Account> accounts = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();

    public ProjectBuilder addId(int id) {
        this.id = id;
        return this;
    }

    public ProjectBuilder addProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public ProjectBuilder addProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
        return this;
    }

    public ProjectBuilder addStatus(boolean status) {
        this.status = status;
        return this;
    }

    public ProjectBuilder addAccounts(List<Account> accounts) {
        this.accounts = accounts;
        return this;
    }

    public ProjectBuilder addTasks(List<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Project build() {
        Project newProject = new Project();
        newProject.setId(id);
        newProject.setProjectName(projectName);
        newProject.setProjectDesc(projectDesc);
        newProject.setStatus(status);
        newProject.setAccounts(accounts);
        newProject.setTasks(tasks);
        return newProject;
    }
}
