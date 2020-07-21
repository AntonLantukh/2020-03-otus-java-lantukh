package ru.otus.lantukh.jdbc.mapper;

import ru.otus.lantukh.core.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> {
    Class clazz;
    Field idField;
    List<Field> allFields;
    List<Field> fieldsWithoutId;

    EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    Constructor<T> getConstructor() {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructors()[0];
            Class<?>[] types = getConstructorParams(constructor);
            Constructor<T> construct = clazz.getConstructor(types);
            construct.setAccessible(true);

            return constructor;
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }
    };

    Class<?>[] getConstructorParams(Constructor<T> constructor) {
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

    Field getIdField() {
        if (idField != null) {
            return idField;
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field: fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            };
        }

        return null;
    };

    List<Field> getAllFields() {
        if (allFields != null) {
            return allFields;
        }

        return Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
    };

    List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId != null) {
            return fieldsWithoutId;
        }

        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldsWithoutId = new ArrayList<>();

        for (Field field: fields) {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldsWithoutId.add(field);
            };
        }

        return fieldsWithoutId;
    };

    String getClassName() {
        return clazz.getSimpleName().toLowerCase();
    }

    String getIdFieldName() {
        return getIdField().getName();
    }


    String getAllFieldsNames() {
        return getAllFields().stream().map(Field::getName).collect(Collectors.joining(", "));
    };

    String getAllFieldsUnknownParams() {
        return getAllFields().stream().map(field -> "?").collect(Collectors.joining(", "));
    };

    String getAllParametrizedFieldsNames() {
        return getAllFields().stream().map(field -> field.getName() + " = ?").collect(Collectors.joining(", "));
    };

}
