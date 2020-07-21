package ru.otus.lantukh.jdbc.mapper;

public class EntitySQLMetaDataImpl<T> {
    EntityClassMetaDataImpl<T> classMetaData;

    public EntitySQLMetaDataImpl(Class<T> clazz) {
        this.classMetaData = new EntityClassMetaDataImpl(clazz);
    }

    String getSelectAllSql() {
        return "SELECT * FROM " + classMetaData.getClassName();
    };

    String getSelectByIdSql() {
        return "SELECT * FROM " + classMetaData.getClassName() +
                " WHERE " + classMetaData.getIdFieldName() + " = ?";
    };

    String getInsertSql() {
        return "INSERT INTO " +
                classMetaData.getClassName() + " (" + classMetaData.getAllFieldsNames() + ")" +
                " VALUES " + "(" +  classMetaData.getAllFieldsUnknownParams() + ")";
    };

    String getUpdateSql() {
        return "UPDATE " + classMetaData.getClassName() +
                " SET " + classMetaData.getAllParametrizedFieldsNames() +
                " WHERE " + classMetaData.getIdFieldName() + " = ?";
    };
}
