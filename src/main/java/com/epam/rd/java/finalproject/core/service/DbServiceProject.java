package com.epam.rd.java.finalproject.core.service;

import com.epam.rd.java.finalproject.core.model.Project;

import java.util.List;
import java.util.Optional;

public interface DbServiceProject {
    Optional<List<Project>> getAllProjects();

    Optional<Project> getProject(int id);

    int saveProject(Project project);

    int updateProject(Project project);
}
