package dev.mednikov.pinky.exceptions;

public final class InterpreterException extends PinkyException{


    public InterpreterException(String message, int lineNumber) {
        super(message, lineNumber);
    }

}
