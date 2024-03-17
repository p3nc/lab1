import java.lang.reflect.*;
import java.util.Arrays;

// Інтерфейс для профілювання та трасування методів
interface MyInterface {
    void myMethod();
}

// Клас, який реалізує MyInterface
class MyClass implements MyInterface {
    @Override
    public void myMethod() {
        try {
            Thread.sleep(1000); // Симуляція тривалого методу
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Метод виконаний");
    }
}

// Проксі-об'єкт для профілювання методу
class ProfilingHandler implements InvocationHandler {
    private Object target;

    public ProfilingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        long endTime = System.currentTimeMillis();
        System.out.println("Метод виконаний за " + (endTime - startTime) + " мілісекунд");
        return result;
    }
}

// Проксі-об'єкт для трасування методу
class TracingHandler implements InvocationHandler {
    private Object target;

    public TracingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Ім'я методу: " + method.getName());
        System.out.println("Аргументи методу:: " + Arrays.toString(args));
        Object result = method.invoke(target, args);
        System.out.println("Результат методу:: " + result);
        return result;
    }
}

public class ProxyUsing {
    public static void main(String[] args) {
        // Профілювання методу
        MyInterface profilingProxy = (MyInterface) Proxy.newProxyInstance(
                ProxyUsing.class.getClassLoader(),
                new Class[]{MyInterface.class},
                new ProfilingHandler(new MyClass())
        );
        profilingProxy.myMethod();

        // Трасування методу
        MyInterface tracingProxy = (MyInterface) Proxy.newProxyInstance(
                ProxyUsing.class.getClassLoader(),
                new Class[]{MyInterface.class},
                new TracingHandler(new MyClass())
        );
        tracingProxy.myMethod();
    }
}

