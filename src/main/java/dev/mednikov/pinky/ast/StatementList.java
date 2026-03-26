package dev.mednikov.pinky.ast;

import java.util.List;

public class StatementList extends Node{

    private final List<Stmt> statements;

    public StatementList(int lineNumber, List<Stmt> statements) {
        super(lineNumber);
        this.statements = statements;
    }

    public List<Stmt> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StatementList{");
        this.statements.forEach(stmt -> builder.append(stmt.toString()));
        builder.append("}");
        return builder.toString();
    }
}
