package ru.otus.lantukh.calculation;

import ru.otus.lantukh.log.LogInvocationHandler;
import java.lang.reflect.Proxy;

public class CalculationProxy {
    public CalculationInterface getProxy() {
        CalculationImpl calculation = new CalculationImpl();

        return (CalculationInterface) Proxy.newProxyInstance(
                CalculationImpl.class.getClassLoader(),
                CalculationImpl.class.getInterfaces(),
                new LogInvocationHandler(calculation)
        );
    }
}
