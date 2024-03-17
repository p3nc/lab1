import java.lang.reflect.Array;

public class ArrayManipulator {

    // Створення одновимірного масиву певного типу та розміру
    public static Object createArray(Class<?> type, int length) {
        return Array.newInstance(type, length);
    }

    // Створення матриці певного типу та розмірів
    public static Object createMatrix(Class<?> type, int rows, int cols) {
        return Array.newInstance(type, rows, cols);
    }

    // Зміна розмірів масиву зі збереженням значень
    public static Object resizeArray(Object array, int newLength) {
        Class<?> type = array.getClass().getComponentType();
        Object newArray = Array.newInstance(type, newLength);
        int length = Math.min(Array.getLength(array), newLength);
        System.arraycopy(array, 0, newArray, 0, length);
        return newArray;
    }

    // Зміна розмірів матриці зі збереженням значень
    public static Object resizeMatrix(Object matrix, int newRows, int newCols) {
        Class<?> type = matrix.getClass().getComponentType().getComponentType();
        Object newMatrix = Array.newInstance(type, newRows, newCols);
        int rows = Math.min(Array.getLength(matrix), newRows);
        for (int i = 0; i < rows; i++) {
            Object row = Array.get(matrix, i);
            Object newRow = Array.newInstance(type, newCols);
            int cols = Math.min(Array.getLength(row), newCols);
            System.arraycopy(row, 0, newRow, 0, cols);
            Array.set(newMatrix, i, newRow);
        }
        return newMatrix;
    }

    // Перетворення одновимірного масиву на рядок
    public static String arrayToString(Object array) {
        StringBuilder sb = new StringBuilder();
        int length = Array.getLength(array);
        sb.append(array.getClass().getComponentType().getName());
        sb.append("[").append(length).append("] = {");
        for (int i = 0; i < length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(Array.get(array, i));
        }
        sb.append("}");
        return sb.toString();
    }

    // Перетворення матриці на рядок
    public static String matrixToString(Object matrix) {
        StringBuilder sb = new StringBuilder();
        int rows = Array.getLength(matrix);
        sb.append(matrix.getClass().getComponentType().getComponentType().getName());
        sb.append("[").append(rows).append("] = {");
        for (int i = 0; i < rows; i++) {
            if (i > 0) sb.append(", ");
            sb.append(arrayToString(Array.get(matrix, i)));
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Приклади використання

        // Створення масиву int розміром 2
        int[] intArray = (int[]) createArray(int.class, 2);
        System.out.println(arrayToString(intArray));

        // Створення масиву рядків розміром 3
        String[] stringArray = (String[]) createArray(String.class, 3);
        System.out.println(arrayToString(stringArray));

        // Зміна розміру масиву на 5
        intArray = (int[]) resizeArray(intArray, 5);
        System.out.println(arrayToString(intArray));

        // Створення масиву Double розміром 5
        Double[] doubleArray = (Double[]) createArray(Double.class, 5);
        System.out.println(arrayToString(doubleArray));

        // Створення матриці int розміром 3x5
        int[][] intMatrix = (int[][]) createMatrix(int.class, 3, 5);
        System.out.println(matrixToString(intMatrix));

        // Зміна розмірів матриці на 4x6
        intMatrix = (int[][]) resizeMatrix(intMatrix, 4, 6);
        System.out.println(matrixToString(intMatrix));

        // Зміна розмірів матриці на 3x7
        intMatrix = (int[][]) resizeMatrix(intMatrix, 3, 7);
        System.out.println(matrixToString(intMatrix));

        // Заповнення матриці значеннями
        for (int i = 0; i < intMatrix.length; i++) {
            for (int j = 0; j < intMatrix[i].length; j++) {
                intMatrix[i][j] = i * intMatrix[i].length + j;
            }
        }

        // Перетворення заповненої матриці на рядок
        System.out.println(matrixToString(intMatrix));
    }
}
