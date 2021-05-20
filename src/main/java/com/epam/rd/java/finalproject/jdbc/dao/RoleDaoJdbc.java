package com.epam.rd.java.finalproject.jdbc.dao;

import com.epam.rd.java.finalproject.core.dao.AbstractDao;
import com.epam.rd.java.finalproject.core.dao.AbstractSql;
import com.epam.rd.java.finalproject.core.dao.DaoException;
import com.epam.rd.java.finalproject.core.model.Role;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import com.epam.rd.java.finalproject.jdbc.DbExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

class RoleDaoJdbc extends AbstractDao<Role> {

    private static final Logger LOGGER = LogManager.getLogger(RoleDaoJdbc.class);

    private final DbExecutor<Role> dbExecutor;
    private final AbstractSql sql;

    public RoleDaoJdbc(SessionManager sessionManager, DbExecutor<Role> dbExecutor, AbstractSql sql) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.sql = sql;
    }

    @Override
    public Optional<Role> selectByField(Object field) {
        return selectQuery(field, sql.selectById()).map(roleList -> roleList.get(0));
    }

    @Override
    public Optional<List<Role>> select() {
        return selectQuery(null, sql.selectAll());
    }

    private Optional<List<Role>> selectQuery(Object field, String sql) {
        try {
            return dbExecutor.executeSelect(getConnection(), sql, field,
                    rs -> {
                        try {
                            List<Role> resultList = new ArrayList<>();
                            while (rs.next()) {
                                Role role = new Role();
                                role.setId(rs.getInt("id"));
                                role.setRoleRu(rs.getString("role_ru"));
                                role.setRoleEn(rs.getString("role_en"));
                                resultList.add(role);
                            }
                            return resultList;
                        } catch (SQLException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                        return null;
                    }
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public int insert(Role data) {
        try {
            return dbExecutor.executeInsert(getConnection(), sql.insert(),
                    Collections.unmodifiableList(Arrays.asList(
                            data.getRoleRu(),
                            data.getRoleEn())
                    )
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }

    }

    @Override
    public int update(Role data) {
        try {
            return dbExecutor.executeInsert(getConnection(), sql.update(),
                    Collections.unmodifiableList(Arrays.asList(
                            data.getRoleRu(),
                            data.getRoleEn(),
                            data.getId())
                    )
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

}
