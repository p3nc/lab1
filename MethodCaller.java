import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}

public class MethodCaller {

    public static void callMethod(Object object, String methodName, Object... parameters) throws FunctionNotFoundException {
        Class<?>[] parameterTypes = Arrays.stream(parameters)
                .map(Object::getClass)
                .toArray(Class[]::new);

        try {
            System.out.println("Викликається метод: " + methodName);
            System.out.println("Доступні методи у об'єкта: ");
            Arrays.stream(object.getClass().getMethods())
                    .map(Method::getName)
                    .forEach(System.out::println);

            Method method = object.getClass().getMethod(methodName, parameterTypes);
            Object result = method.invoke(object, parameters);
            System.out.println("Результат виклику: " + result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new FunctionNotFoundException("Метод " + methodName + " з вказаними параметрами не знайдено.");
        }
    }


    public static void main(String[] args) {
        TestClass testObject = new TestClass();

        try {
            callMethod(testObject, "calculate", 1.0);
            callMethod(testObject, "calculate", 1.0, 1);
        } catch (FunctionNotFoundException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}


