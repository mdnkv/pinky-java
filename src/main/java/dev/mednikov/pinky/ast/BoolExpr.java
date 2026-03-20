package dev.mednikov.pinky.ast;

public class BoolExpr extends Expr {

    private final boolean value;

    public BoolExpr(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Bool [value=" + Boolean.toString(value) + "]";
    }
}
