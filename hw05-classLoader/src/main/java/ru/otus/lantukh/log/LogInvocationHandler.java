package ru.otus.lantukh.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LogInvocationHandler implements InvocationHandler {
    private Set<String> loggedMethods;
    private Object obj;

    public LogInvocationHandler(Object obj) {
        this.obj = obj;
        this.loggedMethods = collectLogAnnotation(obj);
    }

    private String getMethodKey(Method method) {
        return method.getName() +  Arrays.toString(method.getParameterTypes());
    }

    Set<String> collectLogAnnotation(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .map(this::getMethodKey)
                .collect(Collectors.toSet());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if (loggedMethods.contains(getMethodKey(method))) {
            System.out.println("Executed method " + method.getName() + ", params: " + Arrays.toString(args));
        }

        return method.invoke(obj, args);
    }
}

