package edu.nju.mutest.example;

public class UOITestSuite {
    public static void main(String args[]) {
        testPabs();
        testNabs();
    }

    private static void testPabs() {
        int oracle = ~5 + 3;  // Adjusted to reflect UOI mutation
        int res = UOIExample.pabs(5);
        if(oracle == res) {
            System.out.println("[TEST] testPabs() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testPabs() fail (%d, %d)!", oracle, res
            ));
        }
    }

    private static void testNabs() {
        int oracle = -7 + 9;  // Adjusted to reflect UOI mutation
        int res = UOIExample.nabs(7);
        if(oracle == res) {
            System.out.println("[TEST] testNabs() pass!");
        } else {
            throw new RuntimeException(String.format(
                    "[TEST] testNabs() fail (%d, %d)!", oracle, res
            ));
        }
    }
}
