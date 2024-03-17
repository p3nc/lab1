import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

class ExampleObject {
    private int intValue;
    private String stringValue;

    public ExampleObject(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public void printValues() {
        System.out.println("intValue: " + intValue);
        System.out.println("stringValue: " + stringValue);
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}

public class ObjectInspector {
    public static void inspectObject(Object obj) {
        // Виводимо реальний тип об'єкта
        System.out.println("Реальний тип об'єкта: " + obj.getClass().getName());

        // Виводимо стан об'єкта (поля та їх значення)
        System.out.println("Стан об'єкта:");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                System.out.println(field.getName() + ": " + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Виводимо список відкритих методів без параметрів
        System.out.println("Відкриті методи без параметрів:");
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0) {
                System.out.println(method.getName());
            }
        }
    }

    public static void main(String[] args) {
        ExampleObject exampleObject = new ExampleObject(42, "Hello, World!");

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\nМеню:");
            System.out.println("1. Показати інформацію про об'єкт");
            System.out.println("2. Викликати метод об'єкта");
            System.out.println("3. Вийти");
            System.out.print("Виберіть опцію: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    inspectObject(exampleObject);
                    break;
                case 2:
                    System.out.print("Введіть ім'я методу для виклику: ");
                    String methodName = scanner.next();
                    try {
                        Method method = exampleObject.getClass().getDeclaredMethod(methodName);
                        Object result = method.invoke(exampleObject);
                        System.out.println("Результат виклику методу " + methodName + ": " + result);
                    } catch (Exception e) {
                        System.out.println("Помилка: " + e.getMessage());
                    }
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Невірний вибір. Будь ласка, введіть відповідну опцію.");
            }
        }
        scanner.close();
    }
}
