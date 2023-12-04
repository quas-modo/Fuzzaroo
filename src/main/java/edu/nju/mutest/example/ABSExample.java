package edu.nju.mutest.example;

public class ABSExample {
    private ABSExample(){}

    public static int pabs(int a){
        int b = 3;
        a = a + b;
        return a;
    }

    public static int nabs(int a){
        int b = 9;
        a = a + b;
        return a;
    }

    public static int zero(int a){
        int b = 10;
        return a + b;
    }
}
