package dev.mednikov.pinky.ast;

public class PrintStmt extends Stmt {

    private final Expr expr;
    private final boolean newLine;

    public PrintStmt(int lineNumber, Expr expr, boolean newLine) {
        super(lineNumber);
        this.expr = expr;
        this.newLine = newLine;
    }

    public boolean isNewLine() {
        return newLine;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "PrintStmt {" + expr.toString() + "}";
    }
}
