package dev.mednikov.pinky.parser;

import dev.mednikov.pinky.ast.*;
import dev.mednikov.pinky.exceptions.ParserException;
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
            throw new ParserException("Found " + this.previousToken().getLexeme() + " at the end of parsing", this.previousToken().getLineNumber());
        } else if (this.peek().getType() == expected) {
            return this.advance();
        } else {
            throw new ParserException(
                    "Expected " + expected.toString() + ", found " + this.peek().getLexeme() + ".",
                    this.peek().getLineNumber()
            );
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
            return new IntegerExpr(value, this.previousToken().getLineNumber());
        }
        else if (this.match(TOK_FLOAT)) {
            double value = Double.parseDouble(this.previousToken().getLexeme());
            return new FloatExpr(value, this.previousToken().getLineNumber());
        }
        else if (this.match(TOK_TRUE)) {
            return new BoolExpr(true, this.previousToken().getLineNumber());
        }
        else if (this.match(TOK_FALSE)) {
            return new BoolExpr(false, this.previousToken().getLineNumber());
        }
        else if (this.match(TOK_STRING)) {
            String text = this.previousToken()
                    .getLexeme()
                    .replace("\"", "")
                    .replace("'", "");
            return new StrExpr(text, this.previousToken().getLineNumber());
        }
        else  if (this.match(TOK_LPAREN)) {
            Expr expr = this.expr();
            if (!this.match(TOK_RPAREN)) {
                // raise exception
                throw new ParserException("Error: ) expected", this.previousToken().getLineNumber());
            }
            return new Grouping(expr, this.previousToken().getLineNumber());
        }
        throw new ParserException("Parser error", this.previousToken().getLineNumber());
    }

    Expr exponent(){
        Expr expr = this.primary();
        while (this.match(TOK_CARET)){
            Token operator = this.previousToken();
            Expr rightOperand = this.exponent();
            expr = new BinOpExpr(expr, rightOperand, operator, operator.getLineNumber());
        }
        return expr;
    }

    Expr unary() {
        if (this.match(TOK_NOT) || this.match(TOK_MINUS) || this.match(TOK_PLUS)){
            Token operator = this.previousToken();
            Expr operand = this.unary();
            return new UnOpExpr(operand, operator, operator.getLineNumber());
        }
        return this.exponent();
    }

    Expr modulo(){
        Expr expr = this.unary();
        while (this.match(TOK_MOD)){
            Token operator = this.previousToken();
            Expr rightOperand = this.unary();
            expr = new BinOpExpr(expr, rightOperand, operator, operator.getLineNumber());
        }
        return expr;
    }

    Expr multiplication(){
        Expr expr = this.modulo();
        while (this.match(TOK_STAR) || this.match(TOK_SLASH)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.modulo();
            expr = new BinOpExpr(expr, rightOperand, operator, operator.getLineNumber());
        }
        return expr;
    }

    Expr addition (){
        Expr expr = this.multiplication();
        while (this.match(TOK_PLUS) || this.match(TOK_MINUS)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.multiplication();
            expr = new BinOpExpr(expr, rightOperand, operator, operator.getLineNumber());
        }
        return expr;
    }

    Expr comparison(){
        Expr expr = this.addition();
        while (this.match(TOK_GT) || this.match(TOK_LT)
                || this.match(TOK_GE) || this.match(TOK_LE)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.addition();
            expr = new BinOpExpr(expr, rightOperand, operator, operator.getLineNumber());
        }
        return expr;
    }

    Expr equality(){
        Expr expr = this.comparison();
        while (this.match(TOK_NE) || this.match(TOK_EQEQ)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.comparison();
            expr = new BinOpExpr(expr, rightOperand, operator, operator.getLineNumber());
        }
        return expr;
    }

    Expr logicalAnd(){
        Expr expr = this.equality();
        while (this.match(TOK_AND)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.equality();
            expr = new LogicalOpExpr(operator, expr, rightOperand, operator.getLineNumber());
        }
        return expr;
    }

    Expr logicalOr(){
        Expr expr = this.logicalAnd();
        while (this.match(TOK_OR)) {
            Token operator = this.previousToken();
            Expr rightOperand = this.logicalAnd();
            expr = new LogicalOpExpr(operator, expr, rightOperand, operator.getLineNumber());
        }
        return expr;
    }

    Expr expr(){
        return this.logicalOr();
    }

    public Expr parse(){
       return this.expr();
    }


}
