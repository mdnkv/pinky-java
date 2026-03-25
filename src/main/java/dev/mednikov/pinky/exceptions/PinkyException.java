package dev.mednikov.pinky.exceptions;

public sealed class PinkyException extends RuntimeException permits InterpreterException, LexerException, ParserException {

    public PinkyException(String message, int lineNumber) {
        super("[Line " + lineNumber + "]: " + message);
    }
}
