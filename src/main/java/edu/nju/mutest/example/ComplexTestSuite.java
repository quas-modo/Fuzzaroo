package edu.nju.mutest.example;

public class ComplexTestSuite {

    public static void main(String[] args) {
        testIsTriangle();
        testGetType();
        testSort();
    }

    private static void testIsTriangle() {
        ComplexExample triangle = new ComplexExample(3L, 4L, 5L);
        boolean oracle = true; // Expecting true for a valid triangle
        boolean result = triangle.isTriangle();

        if (oracle == result) {
            System.out.println("[TEST] testIsTriangle() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testIsTriangle() fail (%b, %b)!", oracle, result
            ));
        }
    }

    private static void testGetType() {
        ComplexExample triangle = new ComplexExample(3L, 3L, 3L);
        String oracle = "Regular"; // Expecting "Regular" for an equilateral triangle
        String result = triangle.getType();

        if (oracle.equals(result)) {
            System.out.println("[TEST] testGetType() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testGetType() fail (%s, %s)!", oracle, result
            ));
        }
    }

    private static void testSort() {
        String[] arr = {"banana", "apple", "cherry"};
        String[] oracle = {"apple", "banana", "cherry"}; // Sorted array
        ComplexExample.sort(arr);

        if (java.util.Arrays.equals(oracle, arr)) {
            System.out.println("[TEST] testSort() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testSort() fail (%s, %s)!", java.util.Arrays.toString(oracle), java.util.Arrays.toString(arr)
            ));
        }
    }
}

