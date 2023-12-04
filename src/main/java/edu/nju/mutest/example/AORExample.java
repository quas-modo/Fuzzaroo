package edu.nju.mutest.example;

public class AORExample {
    private AORExample() {}

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int divide(int a, int b){return a / b;}

    public static int remainer(int a, int b){return a % b;}
}
