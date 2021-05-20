package com.epam.rd.java.finalproject.jdbc.dao;

import com.epam.rd.java.finalproject.core.dao.DaoException;
import com.epam.rd.java.finalproject.core.dao.ProjectDao;
import com.epam.rd.java.finalproject.core.model.Project;
import com.epam.rd.java.finalproject.core.model.ProjectBuilder;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import com.epam.rd.java.finalproject.jdbc.DbExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

public class ProjectDaoJdbc extends ProjectDao {
    private static final Logger LOGGER = LogManager.getLogger(ProjectDaoJdbc.class);

    private final DbExecutor<Project> dbExecutor;

    public ProjectDaoJdbc(SessionManager sessionManager, DbExecutor<Project> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<Project> selectByField(Object field) {
        return Optional.ofNullable(selectQuery(field, SELECT_BY_ID).get().get(0));
    }

    @Override
    public Optional<List<Project>> select() {
        return selectQuery(null, SELECT_ALL);
    }

    private Optional<List<Project>> selectQuery(Object field, String sql) {
        try {
            return dbExecutor.executeSelect(getConnection(), sql, field,
                    rs -> {
                        try {
                            List<Project> projects = new ArrayList<>();
                            while (rs.next()) {
                                projects.add(
                                        new ProjectBuilder()
                                                .addId(rs.getInt("id"))
                                                .addProjectName(rs.getString("project_name"))
                                                .addProjectDesc(rs.getString("project_desk"))
                                                .build()
                                );
                            }
                            return Collections.unmodifiableList(projects);
                        } catch (SQLException e) {
                            LOGGER.error(e.getMessage(), e);
                            throw new DaoException(e.getMessage(), e);
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error("ProjectDaoJdbc selectQuery error sql: {}", sql);
            throw new DaoException(e.getMessage(), e);
        }
    }

    @Override
    public int insert(Project data) {
        try {
            return dbExecutor.executeInsert(getConnection(), INSERT,
                    Collections.unmodifiableList(Arrays.asList(
                            data.getProjectName(),
                            data.getProjectDesc())
                    )
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public int update(Project data) {
        try {
            return dbExecutor.executeInsert(getConnection(), UPDATE,
                    Collections.unmodifiableList(Arrays.asList(
                            data.getProjectName(),
                            data.getProjectDesc())
                    )
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }
}
