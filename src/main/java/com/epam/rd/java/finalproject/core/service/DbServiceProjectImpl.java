package com.epam.rd.java.finalproject.core.service;

import com.epam.rd.java.finalproject.core.dao.AbstractDao;
import com.epam.rd.java.finalproject.core.dao.ProjectSql;
import com.epam.rd.java.finalproject.core.model.Project;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DbServiceProjectImpl implements DbServiceProject {
    private static final Logger LOGGER = LogManager.getLogger(DbServiceAccountImpl.class);

    private final AbstractDao<Project> projectDao;

    public DbServiceProjectImpl(AbstractDao<Project> projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Optional<List<Project>> getAllProjects() {
        try (SessionManager sessionManager = projectDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<List<Project>> allProjects = projectDao.select();
                LOGGER.info("Roles: {}", allProjects.orElse(null));
                return allProjects;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Project> getProject(int id) {
        try (SessionManager sessionManager = projectDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Project> projectOptional = projectDao.selectByField(id);
                LOGGER.info("Role: {}", projectOptional.orElse(null));
                return projectOptional;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }

    }

    @Override
    public int saveProject(Project role) {
        try (SessionManager sessionManager = projectDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                int accountId = projectDao.insert(role);
                sessionManager.commitSession();
                LOGGER.debug("created account: {}", accountId);
                return accountId;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public int updateProject(Project role) {
        try (SessionManager sessionManager = projectDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                int accountId = projectDao.update(role);
                sessionManager.commitSession();
                LOGGER.debug("updated account: {}", accountId);
                return accountId;
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }
}
