package dev.mednikov.pinky.ast;

public class Grouping extends Expr{

    private final Expr value;

    public Grouping(Expr value) {
        this.value = value;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Grouping [" + this.value.toString() + "]";
    }
}
