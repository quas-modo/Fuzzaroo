package edu.nju.mutest.example;

public class Calculator {

    private Calculator() {}

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int LCR(boolean a, boolean b) {
        boolean c = a || b;
        boolean d = a && b;
        boolean e = a | b;
        boolean f = a & b;
        return 0;
    }
}
