package ru.otus.lantukh.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LogInvocationHandler implements InvocationHandler {
    Object obj;

    public LogInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if (method.isAnnotationPresent(Log.class)) {
            System.out.println("Executed method " + method.getName() + ", params: " + Arrays.toString(args));
        }

        return method.invoke(obj, args);
    }
}

