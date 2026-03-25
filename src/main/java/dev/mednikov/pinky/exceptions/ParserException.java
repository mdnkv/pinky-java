package dev.mednikov.pinky.exceptions;

public final class ParserException extends PinkyException {

    public ParserException(String message, int lineNumber) {
        super(message, lineNumber);
    }

}
