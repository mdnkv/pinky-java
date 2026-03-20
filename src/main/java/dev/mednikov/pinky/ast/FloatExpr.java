package dev.mednikov.pinky.ast;

public class FloatExpr extends Expr {

    private final double value;

    public FloatExpr(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Float [value=" + Double.toString(value) + "]";
    }
}
