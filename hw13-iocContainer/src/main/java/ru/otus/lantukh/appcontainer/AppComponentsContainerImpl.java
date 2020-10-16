package ru.otus.lantukh.appcontainer;

import ru.otus.lantukh.appcontainer.api.AppComponent;
import ru.otus.lantukh.appcontainer.api.AppComponentsContainer;
import ru.otus.lantukh.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?>... configClasses) {
        Arrays.stream(configClasses).forEach(c -> {
            checkConfigClass(c);
            collectAppComponents(c);
        });
    }

    private void collectAppComponents(Class<?> configClass) {
        Method[] methods = configClass.getDeclaredMethods();
        Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getParameterTypes().length))
                .forEach((method) -> {
                    Object obj = getObjectFromMethodWithParams(configClass, method);
                    appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), obj);
                    appComponents.add(obj);
                });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Optional<Object> appComponent = findObjectAmongAppComponents(componentClass);

        return appComponent.isPresent() ? (C) appComponent.get() : null;

    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private Object callMethod(Object object, Method method, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    private Object getObjectFromMethodWithParams(Class<?> configClass, Method method) {
        Class<?>[] methodParams = method.getParameterTypes();
        Object obj;
        Object[] args = Arrays
                .stream(methodParams)
                .map(param -> {
                    Optional<Object> argParam = findObjectAmongAppComponents(param);

                    return argParam.orElseGet(() -> instantiate(param));
                }).toArray();

        obj = callMethod(instantiate(configClass), method, args);

        return obj;
    }

    private Optional<Object> findObjectAmongAppComponents(Class<?> componentClass) {
        return appComponents
                .stream()
                .filter(c -> componentClass.isAssignableFrom(c.getClass()))
                .findFirst();
    }
}
