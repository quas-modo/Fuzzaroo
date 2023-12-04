package edu.nju.mutest.example;

public class UOITestSuite {
    public static void main(String args[]) {
        testBitWise();
        testMinus();
    }

    private static void testBitWise() {
        int oracle = ~5 + 3;  // Adjusted to reflect UOI mutation
        int res = UOIExample.bitWise(5);
        if(oracle == res) {
            System.out.println("[TEST] testPabs() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testPabs() fail (%d, %d)!", oracle, res
            ));
        }
    }

    private static void testMinus() {
        int oracle = -7 + 9;  // Adjusted to reflect UOI mutation
        int res = UOIExample.minus(7);
        if(oracle == res) {
            System.out.println("[TEST] testNabs() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testNabs() fail (%d, %d)!", oracle, res
            ));
        }
    }

    private static void testEqual() {
        boolean oracle = 10 == 10;  // Adjusted to reflect UOI mutation
        boolean res = UOIExample.equal(10);
        if(oracle == res) {
            System.out.println("[TEST] testEqual() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testEqual() fail (%b, %b)!", oracle, res
            ));
        }
    }
}
