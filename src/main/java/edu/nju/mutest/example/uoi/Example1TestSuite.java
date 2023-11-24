package edu.nju.mutest.example.uoi;

public class Example1TestSuite {

    public static void main(String[] args) {
        testIncrementAndReturn();
        testAdd();
        testCheckPositive();
        testPrintCounter();
    }

    private static void testIncrementAndReturn() {
        Example1 example = new Example1();
        int result = example.incrementAndReturn();
        if (result == 1) {
            System.out.println("[TEST] testIncrementAndReturn() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testIncrementAndReturn() fail! Expected 1, but got %d.", result));
        }
    }

    private static void testAdd() {
        Example1 example = new Example1();
        int result = example.add(2, 3);
        if (result == 5) {
            System.out.println("[TEST] testAdd() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testAdd() fail! Expected 5, but got %d.", result));
        }
    }

    private static void testCheckPositive() {
        Example1 example = new Example1();
        boolean isPositive = example.checkPositive(5);
        if (isPositive) {
            System.out.println("[TEST] testCheckPositive() pass!");
        } else {
            throw new RuntimeException("[TEST] testCheckPositive() fail! Expected true.");
        }
    }

    private static void testPrintCounter() {
        Example1 example = new Example1();
        example.printCounter(); // Output-based testing might require additional setup to capture stdout.
        System.out.println("[TEST] testPrintCounter() executed (manual verification required).");
    }
}