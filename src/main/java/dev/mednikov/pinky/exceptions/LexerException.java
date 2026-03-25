package dev.mednikov.pinky.exceptions;

public final class LexerException extends PinkyException{

    public LexerException(String message, int lineNumber) {
        super(message, lineNumber);
    }

}
