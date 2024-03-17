import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.*;

public class ClassAnalyzerGUI extends JFrame {

    private JTextArea textArea;
    private JTextField textField;

    public ClassAnalyzerGUI() {
        setTitle("Аналізатор класів");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Введіть ім'я класу::");
        textField = new JTextField(20);
        JButton analyzeButton = new JButton("Аналізувати");
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = textField.getText();
                if (!className.isEmpty()) {
                    try {
                        String analysisResult = analyzeClass(className);
                        textArea.setText(analysisResult);
                    } catch (ClassNotFoundException ex) {
                        textArea.setText("Клас не знайдено: " + className);
                    }
                } else {
                    textArea.setText("Будь ласка введіть ім'я класу.");
                }
            }
        });

        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(analyzeButton);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String analyzeClass(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return analyzeClass(clazz);
    }

    public String analyzeClass(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        // Package
        Package pkg = clazz.getPackage();
        if (pkg != null) {
            sb.append("Пакет: ").append(pkg.getName()).append("\n");
        }

        // Modifiers and class name
        int modifiers = clazz.getModifiers();
        sb.append("Модифікатори: ").append(Modifier.toString(modifiers)).append("\n");
        sb.append("Ім'я класу: ").append(clazz.getSimpleName()).append("\n");

        // Superclass
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            sb.append("Суперклас: ").append(superclass.getSimpleName()).append("\n");
        }

        // Implemented interfaces
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            sb.append("Імплементовані інтерфейси: ");
            for (Class<?> iface : interfaces) {
                sb.append(iface.getSimpleName()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()); // remove last comma and space
            sb.append("\n");
        }

        // Fields
        Field[] fields = clazz.getDeclaredFields();
        sb.append("Fields:\n");
        for (Field field : fields) {
            sb.append("\t").append(Modifier.toString(field.getModifiers()))
                    .append(" ").append(field.getType().getSimpleName())
                    .append(" ").append(field.getName()).append("\n");
        }

        // Constructors
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        sb.append("Конструктори:\n");
        for (Constructor<?> constructor : constructors) {
            sb.append("\t").append(Modifier.toString(constructor.getModifiers()))
                    .append(" ").append(constructor.getName()).append("(");
            Parameter[] parameters = constructor.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                sb.append(parameters[i].getType().getSimpleName()).append(" ").append(parameters[i].getName());
                if (i < parameters.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")\n");
        }

        // Methods
        Method[] methods = clazz.getDeclaredMethods();
        sb.append("Методи:\n");
        for (Method method : methods) {
            sb.append("\t").append(Modifier.toString(method.getModifiers()))
                    .append(" ").append(method.getReturnType().getSimpleName())
                    .append(" ").append(method.getName()).append("(");
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                sb.append(parameters[i].getType().getSimpleName()).append(" ").append(parameters[i].getName());
                if (i < parameters.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClassAnalyzerGUI().setVisible(true);
            }
        });
    }
}

