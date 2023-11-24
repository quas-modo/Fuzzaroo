package edu.nju.mutest.example.uoi;

public class Example1 {
    private int counter = 0;

    public int incrementAndReturn() {
        counter++; // ExpressionStmt with NameExpr
        return counter; // ReturnStmt with NameExpr
    }

    public int add(int a, int b) {
        return a + b; // ReturnStmt with BinaryExpr
    }

    public boolean checkPositive(int number) {
        return number > 0; // ReturnStmt with BinaryExpr
    }

    public void printCounter() {
        System.out.println(counter); // ExpressionStmt with MethodCallExpr
    }
}
