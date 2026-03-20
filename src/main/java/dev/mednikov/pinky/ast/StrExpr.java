package dev.mednikov.pinky.ast;

public class StrExpr extends Expr {

    private final String value;

    public StrExpr(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Str [value=" + value + "]";
    }
}
