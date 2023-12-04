package edu.nju.mutest.example;

public class UOIExample {
    private UOIExample() {}

    public static int bitWise(int a) {
        int b = 3;
        a = ~a + b;  // Unary operator applied: BITWISE_COMPLEMENT
        return a;
    }

    public static int minus(int a) {
        int b = 9;
        a = -a + b;  // Unary operator applied: MINUS
        return a;
    }

    public static boolean equal(int a) {
        int b = 10;
        return a == b;  // Binary operator applied: EQUAL
    }
}
