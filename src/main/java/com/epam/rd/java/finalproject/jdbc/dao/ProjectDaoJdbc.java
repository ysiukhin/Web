package com.epam.rd.java.finalproject.jdbc.dao;

import com.epam.rd.java.finalproject.core.dao.AbstractDao;
import com.epam.rd.java.finalproject.core.dao.AbstractSql;
import com.epam.rd.java.finalproject.core.dao.DaoException;
import com.epam.rd.java.finalproject.core.model.Project;
import com.epam.rd.java.finalproject.core.model.ProjectBuilder;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import com.epam.rd.java.finalproject.jdbc.DbExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

class ProjectDaoJdbc extends AbstractDao<Project> {
    private static final Logger LOGGER = LogManager.getLogger(ProjectDaoJdbc.class);

    private final DbExecutor<Project> dbExecutor;
    private final AbstractSql sql;

    public ProjectDaoJdbc(SessionManager sessionManager, DbExecutor<Project> dbExecutor, AbstractSql sql) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.sql = sql;
    }

    @Override
    public Optional<Project> selectByField(Object field) {
        return Optional.ofNullable(selectQuery(field, sql.selectById()).get().get(0));
    }

    @Override
    public Optional<List<Project>> select() {
        return selectQuery(null, sql.selectAll());
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
            return dbExecutor.executeInsert(getConnection(), sql.insert(),
                    Collections.unmodifiableList(Arrays.asList(
                            data.getProjectName(),
                            data.getProjectDesc(),
                            data.getStatus())

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
            return dbExecutor.executeInsert(getConnection(), sql.update(),
                    Collections.unmodifiableList(Arrays.asList(
                            data.getProjectName(),
                            data.getProjectDesc(),
                            data.getStatus(),
                            data.getId())
                    )
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }
}
