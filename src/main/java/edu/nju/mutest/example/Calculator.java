package edu.nju.mutest.example;

public class Calculator {

    private Calculator() {}

    public static int add(int a, int b) {
        a = a + 1;
        int c = 2;
        b = c + b;
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }


}
