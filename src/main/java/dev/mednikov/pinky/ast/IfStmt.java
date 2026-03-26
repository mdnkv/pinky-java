package dev.mednikov.pinky.ast;

public class IfStmt extends Stmt{

    private final Expr condition;
    private final StatementList thenStmts;
    private final StatementList elseStmts;

    public IfStmt(int lineNumber, Expr condition, StatementList thenStmts, StatementList elseStmts) {
        super(lineNumber);
        this.condition = condition;
        this.thenStmts = thenStmts;
        this.elseStmts = elseStmts;
    }

    public Expr getCondition() {
        return condition;
    }

    public StatementList getThenStmts() {
        return thenStmts;
    }

    public StatementList getElseStmts() {
        return elseStmts;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IfStmt [condition=");
        builder.append(condition);
        builder.append(", thenStmts=");
        builder.append(thenStmts);
        builder.append(", elseStmts=");
        builder.append(elseStmts);
        builder.append("]");
        return builder.toString();
    }
}
