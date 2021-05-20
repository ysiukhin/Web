package com.epam.rd.java.finalproject.jdbc.dao;

import com.epam.rd.java.finalproject.core.dao.AbstractDao;
import com.epam.rd.java.finalproject.core.dao.DaoException;
import com.epam.rd.java.finalproject.core.sessionmanager.SessionManager;
import com.epam.rd.java.finalproject.jdbc.DbExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

public class AbstractDaoJdbc<T> extends AbstractDao<T> {

    private final Logger logger = LogManager.getLogger(getClass());

    private final DbExecutor<T> dbExecutor;

    public AbstractDaoJdbc(SessionManager sessionManager, DbExecutor<T> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<T> selectByField(Object field) {
        return selectQuery(field, SELECT_BY_ID).map(list -> list.get(0));
    }

    @Override
    public Optional<List<T>> select() {
        return selectQuery(null, SELECT_ALL);
    }

    private Optional<List<T>> selectQuery(Object field, String sql) {
        try {
            return dbExecutor.executeSelect(getConnection(), sql, field,
                    rs -> {
                        try {
                            List<T> resultList = new ArrayList<>();
                            while (rs.next()) {

//                                T role = new Role();
//                                role.setId(rs.getInt("id"));
//                                role.setRoleRu(rs.getString("role_ru"));
//                                role.setRoleEn(rs.getString("role_en"));
//                                resultList.add(role);
                            }
                            return resultList;
                        } catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return null;
                    }
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public int insert(T data) {
        try {
            return dbExecutor.executeInsert(getConnection(), INSERT,
                    Collections.unmodifiableList(Arrays.asList(
//                            data.getRoleRu(),
//                            data.getRoleEn()
                            )
                    )
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }

    }

    @Override
    public int update(T data) {
        try {
            return dbExecutor.executeInsert(getConnection(), UPDATE,
                    Collections.unmodifiableList(Arrays.asList(
//                            data.getRoleRu(),
//                            data.getRoleEn()
                            )
                    )
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

}
