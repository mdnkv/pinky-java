package dev.mednikov.pinky.lexer;

public enum TokenType {
    // Single char tokens
    TOK_LPAREN, TOK_RPAREN,
    TOK_LCURLY, TOK_RCURLY,
    TOK_LSQUAR, TOK_RSQUAR,
    TOK_COMMA,
    TOK_DOT,
    TOK_PLUS,
    TOK_MINUS,
    TOK_STAR,
    TOK_SLASH,
    TOK_CARET,
    TOK_MOD,
    TOK_COLON,
    TOK_SEMICOLON,
    TOK_QUESTION,
    TOK_NOT,
    TOK_GT,
    TOK_LT,

    // Two char tokens
    TOK_EQ, TOK_EQEQ,
    TOK_GE, TOK_LE, TOK_NE, TOK_GTGT, TOK_LTLT,
    TOK_ASSIGN,

    // Literals
    TOK_IDENTIFIER, TOK_STRING, TOK_INTEGER, TOK_FLOAT,

    // Keywords
    TOK_IF, TOK_ELSE, TOK_THEN, TOK_TRUE, TOK_FALSE,
    TOK_AND, TOK_OR, TOK_WHILE, TOK_DO, TOK_FOR, TOK_FUNC,
    TOK_NULL, TOK_END,
    TOK_PRINT, TOK_PRINTLN, TOK_RET
}
