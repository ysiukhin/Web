package com.epam.rd.java.finalproject.core.model;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
    private int id;
    private String projectName;
    private String projectDesc;
    private boolean status;
    private List<Account> projectExecutors;
    private List<Task> projectTasks;
}
