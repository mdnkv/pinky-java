package dev.mednikov.pinky.ast;

import dev.mednikov.pinky.lexer.Token;

public class LogicalOpExpr extends Expr {

    private final Token operator;
    private final Expr leftOperand;
    private final Expr rightOperand;

    public LogicalOpExpr(Token operator, Expr leftOperand, Expr rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public Token getOperator() {
        return operator;
    }

    public Expr getLeftOperand() {
        return leftOperand;
    }

    public Expr getRightOperand() {
        return rightOperand;
    }

    @Override
    public String toString() {
        return "LogicalOperator [" + this.operator.getLexeme()
                + "," + this.leftOperand.toString()
                + "," + this.rightOperand.toString()
                + "]";
    }
}
