package ru.otus.lantukh.tester;

import jdk.jfr.Description;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestLauncher {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Description("Entry point")
    public void start(String className) {
        Class<?> clazz = getClassFromString(className);
        Method[] methods = clazz.getDeclaredMethods();

        ArrayList<Method> beforeMethods = getMethodsFromAnnotation(methods, Before.class);
        ArrayList<Method> afterMethods = getMethodsFromAnnotation(methods, After.class);
        ArrayList<Method> testMethods = getMethodsFromAnnotation(methods, Test.class);

        if (testMethods.isEmpty()) {
            throw new Error("Methods with @Test annotation were not set");
        }

        launchTests(testMethods, beforeMethods, afterMethods, clazz);
    }

    @Description("Launches tests")
    private void launchTests(ArrayList<Method> testMethods, ArrayList<Method> beforeMethods, ArrayList<Method> afterMethods, Class<?> clazz)  {
        int testsCount = testMethods.size();
        int passed = 0;
        int failed = 0;

        for (int i = 0; i < testMethods.size(); i++) {
            // Instantiating object for every test case
            Object target = instantiate(clazz);
            Method method = testMethods.get(i);

            runCondition(target, beforeMethods);

            int testNumber = i + 1;

            try {
                callMethod(target, method);
                passed = passed + 1;
                System.out.println(ANSI_GREEN + testNumber + ". " + "Test " + method.getName() + " passed!" + ANSI_RESET);
            } catch (Exception err) {
                failed = failed + 1;
                System.out.println(ANSI_RED + testNumber + ". " + "Test " + method.getName() + " failed!" + ANSI_RESET);
                System.out.println(err);
            }

            runCondition(target, afterMethods);
        }

        printStatistics(testsCount, passed, failed);
    }

    @Description("Runs Before / After condition")
    private void runCondition(Object target, ArrayList<Method> methods) {
        for (int i = 0; i < methods.size(); i++) {
            Method method = methods.get(i);
            try {
                callMethod(target, method);
            } catch (Exception err) {
                System.out.println(err);
            }
        }
    }

    @Description("Prints statistics")
    private void printStatistics(int total, int passed, int failed) {
        System.out.println("======================");
        System.out.println("Tests count: " + total);
        System.out.println("Total passed tests: " + passed);
        System.out.println("Total failed tests: " + failed);
    }

    @Description("Calls object's method")
    private Object callMethod(Object object, Method method) {
        try {
            method.setAccessible(true);

            return method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Description("Returns object to process test methods on")
    private <T> T instantiate(Class<T> clazz) throws RuntimeException {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();

            return constructor.newInstance();
        } catch (Exception err) {
            throw new RuntimeException(err);
        }
    }

    @Description("Returns class from given string")
    private Class<?> getClassFromString(String className) {
        try {
            return Class.forName("ru.otus.lantukh." + className);
        } catch(Exception err) {
            throw new RuntimeException(err);
        }
    }

    @Description("Returns methods via given annotation")
    private ArrayList<Method> getMethodsFromAnnotation(Method[] methods, Class clazz) {
        ArrayList<Method> annotatedMethods = new ArrayList<>();

        for (Method method: methods) {
            if (method.isAnnotationPresent(clazz)) {
                annotatedMethods.add(method);
            };
        }

        return annotatedMethods;
    }
}
