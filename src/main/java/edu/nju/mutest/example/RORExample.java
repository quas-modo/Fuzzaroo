package edu.nju.mutest.example;

public class RORExample {

    private RORExample() {}


    public static boolean less(int a, int b) {
        return a < b;
    }

    public static boolean less_equal(int a, int b) {
        return a <= b;
    }

    public static boolean greater(int a, int b) {
        return a > b;
    }

    public static boolean greater_equal(int a, int b) {
        return a >= b;
    }

    public static boolean equal(int a, int b) {
        return a == b;
    }

    public static boolean not_equal(int a, int b) {
        return a != b;
    }


}
