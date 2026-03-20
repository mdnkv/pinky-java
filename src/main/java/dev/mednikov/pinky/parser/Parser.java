package dev.mednikov.pinky.parser;

import dev.mednikov.pinky.ast.*;
import dev.mednikov.pinky.lexer.Token;
import dev.mednikov.pinky.lexer.TokenType;

import java.util.List;

import static dev.mednikov.pinky.lexer.TokenType.*;

public class Parser {

    private List<Token> tokens;
    private int curr;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.curr = 0;
    }

    Token advance(){
        return this.tokens.get(curr++);
    }

    Token peek(){
        return this.tokens.get(curr);
    }

    boolean isNext (TokenType expected){
        if (this.curr >= this.tokens.size()) return false;
        return this.peek().getType() == expected;
    }

    Token expect (TokenType expected){
        if (this.curr >= tokens.size()) {
            // todo exception
            throw new RuntimeException();
        } else if (this.peek().getType() == expected) {
            return this.advance();
        } else {
            // todo exception
            throw new RuntimeException();
        }
    }

    Token previousToken(){
        int pos = this.curr - 1;
        return this.tokens.get(pos);
    }

    boolean match(TokenType expected){
        if (this.curr >= this.tokens.size()) {
            return false;
        }
        if (this.peek().getType() != expected) {
            return false;
        }
        this.curr ++;
        return true;
    }

    Expr primary(){
        if (this.match(TOK_INTEGER)) {
            int value = Integer.parseInt(this.previousToken().getLexeme());
            return new IntegerExpr(value);
        }
        if (this.match(TOK_FLOAT)) {
            double value = Double.parseDouble(this.previousToken().getLexeme());
            return new FloatExpr(value);
        }
        if (this.match(TOK_LPAREN)) {
            Expr expr = this.expr();
            if (!this.match(TOK_RPAREN)) {
                // raise exception
                throw new RuntimeException();
            }
            return new Grouping(expr);
        }
        // todo exception
        throw new RuntimeException();
    }

    Expr unary() {
        if (this.match(TOK_NOT) || this.match(TOK_MINUS) || this.match(TOK_PLUS)){
            Token operator = this.previousToken();
            Expr operand = this.unary();
            return new UnOpExpr(operand, operator);
        }
        return this.primary();
    }

    Expr factor(){
        return this.unary();
    }

    Expr term(){
        Expr expr = this.factor();
        while (this.match(TOK_STAR) || this.match(TOK_SLASH)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.factor();
            expr = new BinOpExpr(expr, rightOperand, operator);
        }
        return expr;
    }

    Expr expr (){
        Expr expr = this.term();
        while (this.match(TOK_PLUS) || this.match(TOK_MINUS)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.term();
            expr = new BinOpExpr(expr, rightOperand, operator);
        }
        return expr;
    }

    public Expr parse(){
       return this.expr();
    }


}
