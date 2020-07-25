package ru.otus.lantukh.jdbc.mapper;

import ru.otus.lantukh.core.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private Class clazz;
    private String className;
    private Constructor<T> constructor;
    private Field idField;
    private List<Field> allFields;
    private List<Field> allFieldsWithoutId;

    EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.className = calculateName();
        this.constructor = calculateConstructor();
        this.idField = calculateIdField();
        this.allFields = calculateAllFields();
        this.allFieldsWithoutId = calculateFieldsWithoutId();
    }

    public Constructor<T> getConstructor() {
        return constructor;
    };

    public Field getIdField() {
        return idField;
    };

    public List<Field> getAllFields() {
        return allFields;
    };

    public List<Field> getFieldsWithoutId() {
        return allFieldsWithoutId;
    };

    public String getName() {
        return className;
    }

    private String calculateName() {
        return clazz.getSimpleName().toLowerCase();
    }

    private Constructor<T> calculateConstructor() {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructors()[1];
            Class<?>[] types = getConstructorParams(constructor);
            Constructor<T> construct = clazz.getConstructor(types);
            construct.setAccessible(true);

            return constructor;
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }
    };

    private Field calculateIdField() {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field: fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            };
        }

        return null;
    };

    private List<Field> calculateAllFields() {
        return Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
    };

    private List<Field> calculateFieldsWithoutId() {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldsWithoutId = new ArrayList<>();

        for (Field field: fields) {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldsWithoutId.add(field);
            };
        }

        return fieldsWithoutId;
    };

    private Class<?>[] getConstructorParams(Constructor<T> constructor) {
        Class<?>[] types = constructor.getParameterTypes();

        for(int i = 0; i < types.length; i++) {
            Class<?> type = types[i].getClass();
            if (Float.class == type) {
                types[i] = Float.class;
            } else if (Double.class == type) {
                types[i] = Double.class;
            } else if (Byte.class == type) {
                types[i] = Byte.class;
            } else if (Short.class == type) {
                types[i] = Short.class;
            } else if (Integer.class == type) {
                types[i] = Integer.class;
            } else if (Long.class == type) {
                types[i] = Long.class;
            } else if (Character.class == type) {
                types[i] = Character.class;
            } else if (Boolean.class == type) {
                types[i] = Boolean.class;
            }
        }

        return types;
    }
}
