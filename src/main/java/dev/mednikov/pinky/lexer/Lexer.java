package dev.mednikov.pinky.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.mednikov.pinky.lexer.TokenType.*;

public class Lexer {

    private String source;
    private List<Token> tokens;
    private int start;
    private int curr;
    private int line;

    public Lexer (String source) {
        this.source = source;
        this.start = 0;
        this.curr = 0;
        this.line = 1;
        this.tokens = new ArrayList<>();
    }

    void addToken (TokenType tokenType) {
        String lexeme = source.substring(this.start, this.curr);
        Token token = new Token(tokenType, lexeme, this.line);
        this.tokens.add(token);
    }

    void handleIdentifier(){
        while (Character.isLetterOrDigit(this.peek()) || this.peek() == '_'){
            this.advance();
        }
        String text = this.source.substring(this.start, this.curr);
        // Get keyword
        Optional<TokenType> keyword = LexerUtils.getKeyword(text);
        if (keyword.isPresent()){
            TokenType tokenType = keyword.get();
            this.addToken(tokenType);
        } else {
            this.addToken(TOK_IDENTIFIER);
        }
    }

    void handleNumber(){
        while (Character.isDigit(this.peek())){
            this.advance();
        }
        if (this.peek() == '.' && Character.isDigit(this.lookAhead())){
            // float
            do {
                this.advance();
            } while (Character.isDigit(this.peek()));
            this.addToken(TOK_FLOAT);
        } else {
            this.addToken(TOK_INTEGER);
        }
    }

    void handleString(char startQuote){
        while (this.peek() != startQuote && !(this.curr >= this.source.length())) {
            this.advance();
        }
        if (this.curr >= this.source.length()) {
            // todo raise exception
            throw new RuntimeException("Unexpected end of string");
        }
        this.advance();
        this.addToken(TOK_STRING);
    }

    char advance () {
        return this.source.charAt(this.curr++);
    }

    char lookAhead () {
        if (this.curr == this.source.length()) {
            return '\0';
        }
        int index = this.curr + 1;
        return this.source.charAt(index);
    }

    char peek (){
        if (this.curr == this.source.length()) {
            return '\0';
        }
        return this.source.charAt(this.curr);
    }

    boolean match(char expected){
        if (this.curr >= this.source.length()) {
            return false;
        }
        if (this.source.charAt(this.curr) != expected) {
            return false;
        }
        this.curr++;
        return true;
    }

    public List<Token> tokenize() {
        while (this.curr < this.source.length()) {
            this.start = this.curr;
            // get the next token
            char ch = this.advance();
            if (ch == '\n') {
                this.line++;
            } else if (ch == ' ' || ch == '\t' || ch == '\r') {
                continue;
            } else if (ch == '('){
                this.addToken(TOK_LPAREN);
            } else if (ch == ')'){
                this.addToken(TOK_RPAREN);
            } else if (ch == '{'){
                this.addToken(TOK_LCURLY);
            } else if (ch == '}'){
                this.addToken(TOK_RCURLY);
            } else if (ch == '[') {
                this.addToken(TOK_LSQUAR);
            } else if (ch == ']'){
                this.addToken(TOK_RSQUAR);
            } else if (ch == ',') {
                this.addToken(TOK_COMMA);
            } else if (ch == '.') {
                this.addToken(TOK_DOT);
            } else if (ch == '+') {
                this.addToken(TOK_PLUS);
            }
            // Handle minus
            else if (ch == '-') {
                if (this.match('-')){
                    // process comments
                    while (this.peek() != '\n' && !(this.curr >= this.source.length())) {
                        this.advance();
                    }
                } else {
                    this.addToken(TOK_MINUS);
                }
            }
            else if (ch == '*') {
                this.addToken(TOK_STAR);
            }
            else if (ch == '/') {
                this.addToken(TOK_SLASH);
            }
            else if (ch == '^'){
                this.addToken(TOK_CARET);
            }
            else if (ch == ';'){
                this.addToken(TOK_SEMICOLON);
            }
            else if (ch == '?'){
                this.addToken(TOK_QUESTION);
            }
            else if (ch == '%'){
                this.addToken(TOK_MOD);
            }
            // Handle tokens starting with equal sign
            else if (ch == '='){
                if (this.match('=')){
                    this.addToken(TOK_EQ);
                }
            }
            // Handle ~
            else if (ch == '~'){
                if (this.match('=')){
                    this.addToken(TOK_NE);
                } else {
                    this.addToken(TOK_NOT);
                }
            }
            // Handle <
            else if (ch == '<'){
                if (this.match('=')){
                    this.addToken(TOK_LE);
                } else {
                    this.addToken(TOK_LT);
                }
            }
            // Handle >
            else if (ch == '>'){
                if (this.match('=')){
                    this.addToken(TOK_GE);
                } else {
                    this.addToken(TOK_GT);
                }
            }
            // Handle :
            else if (ch == ':'){
                if (this.match('=')){
                    this.addToken(TOK_ASSIGN);
                } else {
                    this.addToken(TOK_COLON);
                }
            }
            // Handle strings
            else if (ch == '\'' || ch == '"'){
                this.handleString(ch);
            }
            // Handle number
            else if (Character.isDigit(ch)){
                this.handleNumber();
            }
            // handle identifiers
            else if (Character.isLetterOrDigit(ch) || ch == '_'){
                this.handleIdentifier();
            }
        }
        return this.tokens;
    }
}
