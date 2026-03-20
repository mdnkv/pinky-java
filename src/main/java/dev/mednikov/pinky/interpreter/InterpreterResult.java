package dev.mednikov.pinky.interpreter;

public class InterpreterResult {

    private final Object value;
    private final RuntimeType runtimeType;

    public InterpreterResult(Object value, RuntimeType runtimeType) {
        this.value = value;
        this.runtimeType = runtimeType;
    }

    public Object getValue() {
        return value;
    }

    public RuntimeType getRuntimeType() {
        return runtimeType;
    }

    @Override
    public String toString() {
        return runtimeType.toString() + ", " + value;
    }
}
