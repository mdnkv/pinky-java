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
        else if (this.match(TOK_FLOAT)) {
            double value = Double.parseDouble(this.previousToken().getLexeme());
            return new FloatExpr(value);
        }
        else if (this.match(TOK_TRUE)) {
            return new BoolExpr(true);
        }
        else if (this.match(TOK_FALSE)) {
            return new BoolExpr(false);
        }
        else if (this.match(TOK_STRING)) {
            String text = this.previousToken()
                    .getLexeme()
                    .replace("\"", "")
                    .replace("'", "");
            return new StrExpr(text);
        }
        else  if (this.match(TOK_LPAREN)) {
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

    Expr multiplication(){
        Expr expr = this.unary();
        while (this.match(TOK_STAR) || this.match(TOK_SLASH)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.unary();
            expr = new BinOpExpr(expr, rightOperand, operator);
        }
        return expr;
    }

    Expr addition (){
        Expr expr = this.multiplication();
        while (this.match(TOK_PLUS) || this.match(TOK_MINUS)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.multiplication();
            expr = new BinOpExpr(expr, rightOperand, operator);
        }
        return expr;
    }

    Expr expr(){
        return this.addition();
    }

    public Expr parse(){
       return this.expr();
    }


}
