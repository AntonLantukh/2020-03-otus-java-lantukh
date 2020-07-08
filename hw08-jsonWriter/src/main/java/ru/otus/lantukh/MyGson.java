package ru.otus.lantukh;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import java.util.Collection;

public class MyGson {
    public String toJson(Object obj) {
        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        Class<?> clazz = obj.getClass();
        Field[] fieldsAll = clazz.getDeclaredFields();

        if (fieldsAll.length == 0) {
            return buildJson(jsonObject);
        }

        for (int i = 0; i < fieldsAll.length; i++) {
            Field field = fieldsAll[i];
            Class<?> fieldClazz = field.getType();
            String name = field.getName();
            Object value = getFieldValue(obj, field);

            if (isPrimitiveField(fieldClazz)) {
                addPrimitiveToObject(jsonObject, name, value);
            }

            if (isString(fieldClazz)) {
                addString(jsonObject, name, value);
            }

            if (isArrayField(fieldClazz)) {
                addArray(jsonObject, name, value);
            }

            if (isCollectionField(fieldClazz)) {
                addCollection(jsonObject, name, value);
            }
        }

        return buildJson(jsonObject);
    }

    private String buildJson(JsonObjectBuilder jsonObject) {
        JsonObject json = jsonObject.build();
        System.out.println(json);

        return json.toString();
    }

    private Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void addString(JsonObjectBuilder jsonObject, String name, Object value) {
        if (value == null) {
            jsonObject.addNull(name);
        } else {
            jsonObject.add(name, (String) value);
        }
    }

    private void addPrimitiveToObject(JsonObjectBuilder jsonObject, String name, Object value) {
        if (Float.class.isAssignableFrom(value.getClass())) {
            jsonObject.add(name, (Float) value);
        } else if (Double.class.isAssignableFrom(value.getClass())) {
            jsonObject.add(name, (Double) value);
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            jsonObject.add(name, (Integer) value);
        } else if (Character.class.isAssignableFrom(value.getClass())) {
            jsonObject.add(name, (Character) value);
        } else if (Boolean.class.isAssignableFrom(value.getClass())) {
            jsonObject.add(name, (Boolean) value);
        }
    }

    private void addPrimitiveToArray(JsonArrayBuilder jsonArray, Object value) {
        if (Float.class.isAssignableFrom(value.getClass())) {
            jsonArray.add((Float) value);
        } else if (Double.class.isAssignableFrom(value.getClass())) {
            jsonArray.add((Double) value);
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            jsonArray.add((Integer) value);
        } else if (Character.class.isAssignableFrom(value.getClass())) {
            jsonArray.add((Character) value);
        } else if (Boolean.class.isAssignableFrom(value.getClass())) {
            jsonArray.add((Boolean) value);
        }
    }

    private void addArray(JsonObjectBuilder jsonObject, String name, Object value) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();

        for (int i = 0; i < Array.getLength(value); i++) {
            Object element = Array.get(value, i);
            if (element == null) {
                jsonArray.addNull();
            } else if (isPrimitiveField(element.getClass())) {
                addPrimitiveToArray(jsonArray, element);
            } else if (isString(element.getClass())) {
                jsonArray.add(element.toString());
            }
        }

        jsonObject.add(name, jsonArray);
    }

    private void addCollection(JsonObjectBuilder jsonObject, String name, Object value) {
        Collection<?> collection = (Collection<?>) value;
        Object[] arrayValue = collection.toArray();
        addArray(jsonObject, name, arrayValue);
    }

    private boolean isPrimitiveWrapper(Class<?> fieldClazz) {
        return fieldClazz == Double.class
                || fieldClazz == Float.class
                || fieldClazz == Long.class
                || fieldClazz == Integer.class
                || fieldClazz == Short.class
                || fieldClazz == Character.class
                || fieldClazz == Byte.class
                || fieldClazz == Boolean.class;
    }

    private boolean isPrimitiveField(Class<?> fieldClazz) {
        return fieldClazz.isPrimitive() || isPrimitiveWrapper(fieldClazz);
    }

    private boolean isArrayField(Class<?> fieldClazz) {
        return fieldClazz.isArray();
    }

    private boolean isString(Class<?> fieldClazz) {
        return String.class.isAssignableFrom(fieldClazz);
    }

    private boolean isCollectionField(Class<?> fieldClazz) {
        return Collection.class.isAssignableFrom(fieldClazz);
    }
}
