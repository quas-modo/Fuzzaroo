package edu.nju.mutest.example;

public class AORTestSuite {

    public static void main(String[] args) {
        testAdd();
        testMul();
        testSub();
        testDiv();
        testRem();
    }

    private static void testAdd() {
        int oracle = 4;
        int res = AORExample.add(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testAdd() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testAdd() fail (%d, %d)!", oracle, res));
    }

    private static void testSub() {
        int oracle = 2;
        int res = AORExample.subtract(5, 3);
        if (oracle == res)
            System.out.println("[TEST] testSub() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testSub() fail (%d, %d)!", oracle, res));
    }

    private static void testMul() {
        int oracle = 4;
        int res = AORExample.multiply(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testMul() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testMul() fail (%d, %d)!", oracle, res));
    }

    private static void testDiv(){
        int oracle = 9;
        int res = AORExample.divide(63, 7);
        if (oracle == res)
            System.out.println("[TEST] testDiv() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testDiv() fail (%d, %d)!", oracle, res));
    }

    private static void testRem(){
        int oracle = 22;
        int res = AORExample.remainer(45, 23);
        if (oracle == res)
            System.out.println("[TEST] testRem() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testRem() fail (%d, %d)!", oracle, res));
    }

}
