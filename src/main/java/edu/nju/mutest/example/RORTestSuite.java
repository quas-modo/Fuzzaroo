package edu.nju.mutest.example;

/**
 * Demo test suite for {@link RORExample}
 */
public class RORTestSuite {

    public static void main(String[] args) {
        testLes();
        testGre();
        testLesE();
        testGreE();
        testEqu();
        testNotE();
    }
    private static void testLes() {
        boolean oracle = false;
        boolean res = RORExample.less(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testLes() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testLes() fail (%b, %b)!", oracle, res));
    }
    private static void testGre() {
        boolean oracle = false;
        boolean res = RORExample.greater(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testGre() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testGre() fail (%b, %b)!", oracle, res));
    }
    private static void testLesE() {
        boolean oracle = true;
        boolean res = RORExample.less_equal(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testLesE() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testLesE() fail (%b, %b)!", oracle, res));
    }
    private static void testGreE() {
        boolean oracle = true;
        boolean res = RORExample.greater_equal(5, 5);
        if (oracle == res)
            System.out.println("[TEST] testGreE() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testGreE() fail (%b, %b)!", oracle, res));
    }
    private static void testEqu() {
        boolean oracle = true;
        boolean res = RORExample.equal(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testEqu() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testEqu() fail (%b, %b)!", oracle, res));
    }
    private static void testNotE() {
        boolean oracle = false;
        boolean res = RORExample.not_equal(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testNotE() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testNotE() fail (%b, %b)!", oracle, res));
    }

//

}
