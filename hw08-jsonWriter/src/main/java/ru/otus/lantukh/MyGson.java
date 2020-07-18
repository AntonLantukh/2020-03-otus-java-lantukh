package ru.otus.lantukh;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import java.util.Collection;

public class MyGson {
    final char DM = (char) 34;

    public String toJson(Object obj) {
        if (obj == null) {
           return String.valueOf(obj);
        }

        String result;

        if (Float.class.isAssignableFrom(obj.getClass())
                || Double.class.isAssignableFrom(obj.getClass())
                || Number.class.isAssignableFrom(obj.getClass())) {
            result = toNumberJson(obj);
        } else if (Character.class.isAssignableFrom(obj.getClass())
                || String.class.isAssignableFrom(obj.getClass())) {
            result = toStringJson(obj);
        } else if (isArray(obj.getClass())) {
            result = toArrayJson(obj);
        } else if (isCollection(obj.getClass())) {
            result = toCollectionJson(obj);
        } else {
            result = toObjectJson(obj);
        }

        return result;
    }

    private String toNumberJson(Object obj) {
        return obj.toString();
    }

    private String toStringJson(Object obj) {
        return DM + obj.toString() + DM;
    }

    private String toArrayJson(Object obj) {
        JsonArrayBuilder jsonArrayBuilder = createArrayBuilder(obj);

        return jsonArrayBuilder.build().toString();
    }

    private String toCollectionJson(Object obj) {
        Object[] collection = convertCollectionToArray(obj);
        JsonArrayBuilder jsonArrayBuilder = createArrayBuilder(collection);

        return jsonArrayBuilder.build().toString();
    }

    private String toObjectJson(Object obj) {
        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        Class<?> clazz = obj.getClass();
        Field[] fieldsAll = clazz.getDeclaredFields();

        if (fieldsAll.length == 0) {
            return buildObjectJson(jsonObject);
        }

        for (Field field : fieldsAll) {
            Class<?> fieldClazz = field.getType();
            String name = field.getName();
            Object value = getFieldValue(obj, field);

            if (isPrimitiveField(fieldClazz)) {
                addPrimitiveToObject(jsonObject, name, value);
            }

            if (isString(fieldClazz)) {
                addStringToObject(jsonObject, name, value);
            }

            if (isArray(fieldClazz)) {
                addArrayToObject(jsonObject, name, value);
            }

            if (isCollection(fieldClazz)) {
                addCollectionToObject(jsonObject, name, value);
            }
        }

        return buildObjectJson(jsonObject);
    }

    private String buildObjectJson(JsonObjectBuilder jsonObject) {
        JsonObject json = jsonObject.build();

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

    private void addStringToObject(JsonObjectBuilder jsonObject, String name, Object value) {
        if (value == null) {
            jsonObject.addNull(name);
        } else {
            jsonObject.add(name, (String) value);
        }
    }

    private void addPrimitiveToObject(JsonObjectBuilder jsonObject, String name, Object value) {
        if (Float.class == value.getClass()) {
            jsonObject.add(name, (Float) value);
        } else if (Double.class == value.getClass()) {
            jsonObject.add(name, (Double) value);
        } else if (Byte.class == value.getClass()) {
            jsonObject.add(name, (Byte) value);
        } else if (Short.class == value.getClass()) {
            jsonObject.add(name, (Short) value);
        } else if (Integer.class == value.getClass()) {
            jsonObject.add(name, (Integer) value);
        } else if (Long.class == value.getClass()) {
            jsonObject.add(name, (Long) value);
        } else if (Character.class == value.getClass()) {
            jsonObject.add(name, value.toString());
        } else if (Boolean.class == value.getClass()) {
            jsonObject.add(name, (Boolean) value);
        }
    }

    private void addPrimitiveToArray(JsonArrayBuilder jsonArray, Object value) {
        if (Float.class == value.getClass()) {
            jsonArray.add((Float) value);
        } else if (Double.class == value.getClass()) {
            jsonArray.add((Double) value);
        } else if (Byte.class == value.getClass()) {
            jsonArray.add((Byte) value);
        } else if (Short.class == value.getClass()) {
            jsonArray.add((Short) value);
        } else if (Integer.class == value.getClass()) {
            jsonArray.add((Integer) value);
        } else if (Long.class == value.getClass()) {
            jsonArray.add((Long) value);
        } else if (Character.class == value.getClass()) {
            jsonArray.add(value.toString());
        } else if (Boolean.class == value.getClass()) {
            jsonArray.add((Boolean) value);
        }
    }

    private void addArrayToObject(JsonObjectBuilder jsonObject, String name, Object value) {
        JsonArrayBuilder jsonArray = createArrayBuilder(value);
        jsonObject.add(name, jsonArray);
    }

    private void addCollectionToObject(JsonObjectBuilder jsonObject, String name, Object value) {
        Object[] arrayValue = convertCollectionToArray(value);
        addArrayToObject(jsonObject, name, arrayValue);
    }

    private JsonArrayBuilder createArrayBuilder(Object array) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();

        for (int i = 0; i < Array.getLength(array); i++) {
            Object element = Array.get(array, i);
            if (element == null) {
                jsonArray.addNull();
            } else if (isPrimitiveField(element.getClass())) {
                addPrimitiveToArray(jsonArray, element);
            } else if (isString(element.getClass())) {
                jsonArray.add(element.toString());
            }
        }

        return jsonArray;
    }

    private Object[] convertCollectionToArray(Object obj) {
        Collection<?> collection = (Collection<?>) obj;

        return collection.toArray();
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

    private boolean isArray(Class<?> fieldClazz) {
        return fieldClazz.isArray();
    }

    private boolean isString(Class<?> fieldClazz) {
        return String.class.isAssignableFrom(fieldClazz);
    }

    private boolean isCollection(Class<?> fieldClazz) {
        return Collection.class.isAssignableFrom(fieldClazz);
    }
}
