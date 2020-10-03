package ru.otus.lantukh.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    private EntityClassMetaData<T> classMetaData;
    private String selectAllSql;
    private String selectByIdSql;
    private String insertSql;
    private String updateSql;

    public EntitySQLMetaDataImpl(Class<T> clazz) {
        classMetaData = new EntityClassMetaDataImpl<T>(clazz);
        selectAllSql = calculateSelectAllSql();
        selectByIdSql = calculateSelectByIdSql();
        insertSql = calculateInsertSql();
        updateSql = calculateUpdateSql();
    }

    public String getSelectAllSql() {
        return selectAllSql;
    };

    public String getSelectByIdSql() {
        return selectByIdSql;
    };

    public String getInsertSql() {
        return insertSql;
    };

    public String getUpdateSql() {
        return updateSql;
    };

    private String calculateSelectAllSql() {
        return "SELECT * FROM " + classMetaData.getName();
    };

    private String calculateSelectByIdSql() {
        return "SELECT * FROM " + classMetaData.getName() +
                " WHERE " + getIdFieldName() + " = ?";
    };

    private String calculateInsertSql() {
        return "INSERT INTO " +
                classMetaData.getName() + " (" + getAllFieldsWithoutIdNames() + ")" +
                " VALUES " + "(" +  getAllFieldsWithoutIdUnknownParams() + ")";
    };

    private String calculateUpdateSql() {
        return "UPDATE " + classMetaData.getName() +
                " SET " + getAllParametrizedFieldsWithoutIdNames() +
                " WHERE " + getIdFieldName() + " = ?";
    };

    private String getIdFieldName() {
        return classMetaData.getIdField().getName();
    }

    private String getAllFieldsWithoutIdNames() {
        return classMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    };

    private String getAllFieldsWithoutIdUnknownParams() {
        return classMetaData.getFieldsWithoutId().stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));
    };

    private String getAllParametrizedFieldsWithoutIdNames() {
        return classMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));
    };
}
