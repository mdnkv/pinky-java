package dev.mednikov.pinky.ast;

import dev.mednikov.pinky.lexer.Token;

public class UnOpExpr extends Expr {

    private final Expr operand;
    private final Token operator;

    public UnOpExpr(Expr operand, Token operator) {
        this.operand = operand;
        this.operator = operator;
    }

    public Expr getOperand() {
        return operand;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "UnOp [" + this.operator.getLexeme() + "," + this.operand.toString() + "]";
    }
}
