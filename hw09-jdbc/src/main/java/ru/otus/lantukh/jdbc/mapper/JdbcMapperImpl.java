package ru.otus.lantukh.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lantukh.core.dao.UserDaoException;
import ru.otus.lantukh.core.sessionmanager.SessionManager;
import ru.otus.lantukh.jdbc.DbExecutor;
import ru.otus.lantukh.jdbc.dao.UserDaoJdbc;
import ru.otus.lantukh.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private final DbExecutor<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);
    private final EntitySQLMetaData<T> sqlMetaData;
    private final EntityClassMetaData<T> classMetaData;

    public JdbcMapperImpl(Class<T> clazz, SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor) {
        this.dbExecutor = dbExecutor;
        this.sessionManager = sessionManager;
        this.sqlMetaData = new EntitySQLMetaDataImpl<T>(clazz);
        this.classMetaData = new EntityClassMetaDataImpl<T>(clazz);
    }

   public long insert(T objectData) {
        try {
            List<Object> args = getFieldsWithoutIdValues(objectData);
            long id = dbExecutor.executeInsert(getConnection(), sqlMetaData.getInsertSql(), args);

            Field fieldWithId = classMetaData.getIdField();
            fieldWithId.setAccessible(true);

            try {
                fieldWithId.setLong(objectData, id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            logger.info("created " + classMetaData.getName().toLowerCase() + ": {}", objectData);

            return id;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    public long update(T objectData) {
        try {
            List<Object> args = getFieldsWithoutIdValues(objectData);
            Object idField = getFieldValue(objectData, classMetaData.getIdField());
            args.add(idField);

            long id =  dbExecutor.executeInsert(getConnection(), sqlMetaData.getUpdateSql(), args);
            logger.info("updated " + classMetaData.getName().toLowerCase() + ": {}", id);

            return id;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    };


    public Optional<T> findById(long id) {
        try {
            return dbExecutor.executeSelect(getConnection(), sqlMetaData.getSelectByIdSql(),
                    id, rs -> {
                try {
                    if (rs.next()) {
                        Constructor<T> constructor = classMetaData.getConstructor();
                        List<Field> fields = classMetaData.getAllFields();

                        T obj = constructor.newInstance(getArgs(fields, rs));
                        logger.info("found " + classMetaData.getName().toLowerCase() + ": {}", obj);

                        return obj;
                    }
                } catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    logger.error(e.getMessage(), e);
                }

                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return Optional.empty();
    };

    public long insertOrUpdate(T objectData) {
        Object idField = getFieldValue(objectData, classMetaData.getIdField());
        Optional<T> obj = findById((long) idField);

        if (obj.isPresent()) {
            return update(objectData);
        }

        return insert(objectData);
    };

    Object[] getArgs(List<Field> fields, ResultSet rs) {
        return fields.stream().map(field -> {
            try {
                return rs.getObject(field.getName());
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        }).toArray();
    }

    List<Object> getFieldsWithoutIdValues(Object objectData) {
        return classMetaData.getFieldsWithoutId().stream()
                .map(field -> getFieldValue(objectData, field))
                .collect(Collectors.toList());
    }

    private Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
