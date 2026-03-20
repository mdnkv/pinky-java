package dev.mednikov.pinky.lexer;

import java.util.Optional;

import static dev.mednikov.pinky.lexer.TokenType.*;

class LexerUtils {

    static Optional<TokenType> getKeyword (String input){
        return switch (input){
            case "if" -> Optional.of(TOK_IF);
            case "else" -> Optional.of(TOK_ELSE);
            case "for" -> Optional.of(TOK_FOR);
            case "while" -> Optional.of(TOK_WHILE);
            case "do" -> Optional.of(TOK_DO);
            case "then" -> Optional.of(TOK_THEN);
            case "true" -> Optional.of(TOK_TRUE);
            case "false" -> Optional.of(TOK_FALSE);
            case "and" -> Optional.of(TOK_AND);
            case "or" -> Optional.of(TOK_OR);
            case "func" -> Optional.of(TOK_FUNC);
            case "null" -> Optional.of(TOK_NULL);
            case "end" -> Optional.of(TOK_END);
            case "print" -> Optional.of(TOK_PRINT);
            case "println" -> Optional.of(TOK_PRINTLN);
            case "ret" -> Optional.of(TOK_RET);
            default -> Optional.empty();
        };
    }
}
