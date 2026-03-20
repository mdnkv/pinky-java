package dev.mednikov.pinky.ast;

import dev.mednikov.pinky.lexer.Token;

public class BinOpExpr extends Expr {

    private final Expr leftOperand;
    private final Expr rightOperand;
    private final Token operator;

    public BinOpExpr(Expr leftOperand, Expr rightOperand, Token operator) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
    }

    public Expr getLeftOperand() {
        return leftOperand;
    }

    public Expr getRightOperand() {
        return rightOperand;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "UnOp [" + this.operator.getLexeme()
                + "," + this.leftOperand.toString()
                + "," + this.rightOperand.toString()
                + "]";
    }
}
