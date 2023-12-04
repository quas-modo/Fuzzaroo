package edu.nju.mutest.example;

public class ABSTestSuite {
    public static void main(String args[]){
        testPabs();
        testNabs();
        testZero();
    }

    private static void testPabs(){
        int oracle = 8;
        int res = ABSExample.pabs(5);
        if(oracle == res){
            System.out.println("[TEST] testPabs() pass!");
        }else{
            throw new RuntimeException(String.format(
                    "[TEST] testPabs() fail (%d, %d)!", oracle, res
            ));
        }
    }

    private static void testNabs(){
        int oracle = 16;
        int res = ABSExample.nabs(7);
        if(oracle == res){
            System.out.println("[TEST] testNabs() pass!");
        }else{
            throw new RuntimeException(String.format(
                    "[TEST] testNabs() fail (%d, %d)!", oracle, res
            ));
        }
    }

    private static void testZero(){
        int oracle = 20;
        int res = ABSExample.zero(10);
        if(oracle == res){
            System.out.println("[TEST] testZero() pass!");
        }else{
            throw new RuntimeException(String.format(
                    "[TEST] testZero() fail (%d, %d)!", oracle, res
            ));
        }
    }
}
