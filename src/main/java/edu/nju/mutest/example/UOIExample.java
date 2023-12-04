package edu.nju.mutest.example;

public class UOIExample {
    private UOIExample() {}

    public static int pabs(int a) {
        int b = 3;
        a = ~a + b;  // Unary operator applied: BITWISE_COMPLEMENT
        return a;
    }

    public static int nabs(int a) {
        int b = 9;
        a = -a + b;  // Unary operator applied: MINUS
        return a;
    }
}
