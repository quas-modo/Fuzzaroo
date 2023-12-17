package edu.nju.mutest.example;

public class LCRTestSuite {
    public static void main(String[] args) {
        testBinaryAnd();
        testBinaryOR();
        testXOR();
        testAnd();
        testOr();
    }
    //5 & 3 = 1
    //1&1=1
    private static void testBinaryAnd() {
        int oracle = 1;
        int res = LCRExample.binaryAnd(1,1);
        if (oracle == res)
            System.out.println("[TEST] testBinaryAnd() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testBinaryAnd() fail (%d, %d)!", oracle, res));
    }
    //5 | 3 = 7
    //1|1=1
    private static void testBinaryOR() {
        int oracle = 7;
        int res = LCRExample.binaryOr(5,3);
        if (oracle == res)
            System.out.println("[TEST] testBinaryOR() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testBinaryOR() fail (%d, %d)!", oracle, res));
    }
    //5 ^ 3 = 6
    private static void testXOR() {
        int oracle = 6;
        int res = LCRExample.XOR(5,3);
        if (oracle == res)
            System.out.println("[TEST] testXOR() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testXOR() fail (%d, %d)!", oracle, res));
    }

    private static void testAnd() {
        boolean oracle = true;
        boolean res = LCRExample.And(true,true);
        if (oracle == res)
            System.out.println("[TEST] testAnd() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testAnd() fail (%b, %b)!", oracle, res));
    }

    private static void testOr() {
        boolean oracle = true;
        boolean res = LCRExample.Or(true,false);
        if (oracle == res)
            System.out.println("[TEST] testOr() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testOr() fail (%b, %b)!", oracle, res));
    }

}
