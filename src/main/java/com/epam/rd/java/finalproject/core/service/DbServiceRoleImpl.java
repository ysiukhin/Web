package com.epam.rd.java.finalproject.core.service;

import com.epam.rd.java.finalproject.core.dao.RoleDao;
import com.epam.rd.java.finalproject.core.model.Role;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DbServiceRoleImpl implements DbServiceRole {
    private static final Logger logger = LogManager.getLogger(DbServiceAccountImpl.class);

    private final RoleDao roleDao;

    public DbServiceRoleImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Optional<List<Role>> getAllRoles() {
        try (SessionManager sessionManager = roleDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<List<Role>> allRoles = roleDao.select();
                logger.info("Roles: {}", allRoles.orElse(null));
                return allRoles;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Role> getRole(int id) {
        try (SessionManager sessionManager = roleDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Role> accountOptional = roleDao.selectByField(id);
                logger.info("Role: {}", accountOptional.orElse(null));
                return accountOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }

    }

    @Override
    public int saveRole(Role role) {
        try (SessionManager sessionManager = roleDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                int accountId = roleDao.insert(role);
                sessionManager.commitSession();
                logger.debug("created account: {}", accountId);
                return accountId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public int updateRole(Role role) {
        try (SessionManager sessionManager = roleDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                int accountId = roleDao.update(role);
                sessionManager.commitSession();
                logger.debug("updated account: {}", accountId);
                return accountId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }
}
