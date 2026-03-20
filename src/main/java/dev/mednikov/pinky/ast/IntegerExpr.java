package dev.mednikov.pinky.ast;

public class IntegerExpr extends Expr {
    
    private final int value;
    
    public IntegerExpr(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Integer [value=" + Integer.toString(value) + "]";
    }
}
