public class TestClass {
    public double calculate(double a) {
        return Math.exp(-Math.abs(a)) * Math.sin(a);
    }

    public double calculate(double a, int x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }
}
