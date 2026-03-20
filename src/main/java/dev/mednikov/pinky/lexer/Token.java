package dev.mednikov.pinky.lexer;

public class Token {

    private final TokenType type;
    private final String lexeme;
    private final int lineNumber;

    public Token(TokenType type, String lexeme, int lineNumber) {
        this.type = type;
        this.lexeme = lexeme;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "(" + this.type.toString() + ", " + this.lexeme + ", " + Integer.toString(this.lineNumber) + ")";
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
