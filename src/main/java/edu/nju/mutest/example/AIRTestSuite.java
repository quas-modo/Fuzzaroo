package edu.nju.mutest.example;

import java.util.Arrays;

public class AIRTestSuite {
    public static void main(String args[]) {

        testValidIndex();
        testInvalidIndex();
        testFindMax();
        testComputeAverage();
        testContains();
        testCopyRange();

    }

    private static void testValidIndex() {
        int[] array = {1, 2, 3, 4, 5};
        int index = 2; // Valid index
        int expected = array[index];
        int result = AIRExample.getElement(array, index);

        if (expected == result) {
            System.out.println("[TEST] testValidIndex() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testValidIndex() fail! Expected %d, got %d", expected, result
            ));
        }
    }

    private static void testInvalidIndex() {
        try {
            int[] array = {1, 2, 3, 4, 5};
            int index = 5; // Invalid index, should be out of bounds
            AIRExample.getElement(array, index);

            // 如果没有抛出异常，则测试失败
            throw new RuntimeException("[TEST] testInvalidIndex() fail! Exception expected.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("[TEST] testInvalidIndex() pass!");
        }
    }

    private static void testFindMax() {
        int[] array = {1, 3, 5, 7, 9};
        int expectedMax = 9;
        int resultMax = AIRExample.findMax(array);

        if (expectedMax == resultMax) {
            System.out.println("[TEST] testFindMax() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testFindMax() fail! Expected %d, got %d", expectedMax, resultMax
            ));
        }
    }

    private static void testComputeAverage() {
        int[] array = {1, 2, 3, 4, 5};
        double expectedAvg = 3.0;
        double resultAvg = AIRExample.computeAverage(array);

        if (expectedAvg == resultAvg) {
            System.out.println("[TEST] testComputeAverage() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testComputeAverage() fail! Expected %f, got %f", expectedAvg, resultAvg
            ));
        }
    }

    private static void testContains() {
        int[] array = {1, 2, 3, 4, 5};
        boolean resultContains = AIRExample.contains(array, 3);

        if (resultContains) {
            System.out.println("[TEST] testContains() pass!");
        } else {
            throw new RuntimeException("[TEST] testContains() fail! Value not found.");
        }
    }

    private static void testCopyRange() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expectedArray = {2, 3, 4};
        int[] resultArray = AIRExample.copyRange(array, 1, 4);

        if (Arrays.equals(expectedArray, resultArray)) {
            System.out.println("[TEST] testCopyRange() pass!");
        } else {
            throw new RuntimeException("[TEST] testCopyRange() fail! Arrays do not match.");
        }
    }
}
