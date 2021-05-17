package com.epam.rd.java.finalproject.jdbc.dao;

import com.epam.rd.java.finalproject.core.dao.RoleDao;
import com.epam.rd.java.finalproject.core.model.Account;
import com.epam.rd.java.finalproject.core.model.AccountBuilder;
import com.epam.rd.java.finalproject.core.model.Role;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import com.epam.rd.java.finalproject.jdbc.DbExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.rd.java.finalproject.jdbc.dao.AccountDaoSql.selectById;

public class RoleDaoJdbc extends RoleDao {

    private static final Logger logger = LogManager.getLogger(RoleDaoJdbc.class);

    private final DbExecutor<Role> dbExecutor;

    public RoleDaoJdbc(SessionManager sessionManager, DbExecutor<Role> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<Role> selectByField(Object field) {
        try {
            return dbExecutor.executeSelect(getConnection(), selectById,
                    field, rs -> {
                        try {
                            if (rs.next()) {
                                Role role = new Role();
                                role.setId(rs.getInt("id"));
                                role.setRoleRu(rs.getString("role_ru"));
                                role.setRoleEn(rs.getString("role_en"));
                                return new Role();
                            }
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<List<Role>> selectAll(Object field) {
        return Optional.empty();
    }

    @Override
    public int insert(Role data) {
        return 0;
    }

    @Override
    public int update(Role data) {
        return 0;
    }

}
