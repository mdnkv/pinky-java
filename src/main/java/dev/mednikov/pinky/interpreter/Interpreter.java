package dev.mednikov.pinky.interpreter;

import dev.mednikov.pinky.ast.*;
import dev.mednikov.pinky.exceptions.InterpreterException;

import static dev.mednikov.pinky.lexer.TokenType.*;

public class Interpreter {

    private boolean checkBothNumberTypes (RuntimeType type1, RuntimeType type2) {
        return type1 == RuntimeType.TYPE_NUMBER && type2 == RuntimeType.TYPE_NUMBER;
    }

    private boolean checkBothStringTypes (RuntimeType type1, RuntimeType type2) {
        return type1 == RuntimeType.TYPE_STRING && type2 == RuntimeType.TYPE_STRING;
    }

    private boolean checkBothBoolTypes (RuntimeType type1, RuntimeType type2) {
        return type1 == RuntimeType.TYPE_BOOL && type2 == RuntimeType.TYPE_BOOL;
    }

    public InterpreterResult interpret (Node node){
        if (node instanceof IntegerExpr expr){
            double value = expr.getValue();
            return new InterpreterResult(value, RuntimeType.TYPE_NUMBER);
        }
        else if (node instanceof FloatExpr expr){
            return new InterpreterResult(expr.getValue(), RuntimeType.TYPE_NUMBER);
        }
        else if (node instanceof BoolExpr expr){
            return new InterpreterResult(expr.getValue(), RuntimeType.TYPE_BOOL);
        }
        else if (node instanceof StrExpr expr){
            return new InterpreterResult(expr.getValue(), RuntimeType.TYPE_STRING);
        }
        else if (node instanceof Grouping expr){
            return interpret(expr.getValue());
        }
        else if (node instanceof BinOpExpr biOp){
            InterpreterResult leftResult = interpret(biOp.getLeftOperand());
            InterpreterResult rightResult = interpret(biOp.getRightOperand());

            Object leftValue = leftResult.getValue();
            Object rightValue = rightResult.getValue();

            RuntimeType leftType = leftResult.getRuntimeType();
            RuntimeType rightType = rightResult.getRuntimeType();

            if (biOp.getOperator().getType() == TOK_PLUS){
                if (checkBothNumberTypes(leftType, rightType)){
                    double sum = (Double) leftValue + (Double) rightValue;
                    return new InterpreterResult(sum, RuntimeType.TYPE_NUMBER);
                } else if (checkBothStringTypes(leftType, rightType)){
                    String str = leftValue.toString() + rightValue.toString();
                    return new InterpreterResult(str, RuntimeType.TYPE_STRING);
                } else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_MINUS){
                if (checkBothNumberTypes(leftType, rightType)) {
                    double diff = (Double) leftValue - (Double) rightValue;
                    return new InterpreterResult(diff, RuntimeType.TYPE_NUMBER);
                } else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_STAR){
                // Multiply two numbers
                if (checkBothNumberTypes(leftType, rightType)) {
                    double result = (Double) leftValue * (Double) rightValue;
                    return new InterpreterResult(result, RuntimeType.TYPE_NUMBER);
                } else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_SLASH){
                if (checkBothNumberTypes(leftType, rightType)) {
                    double div = (Double) leftValue / (Double) rightValue;
                    return new InterpreterResult(div, RuntimeType.TYPE_NUMBER);
                } else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_MOD){
                if (checkBothNumberTypes(leftType, rightType)) {
                    double div = (Double) leftValue % (Double) rightValue;
                    return new InterpreterResult(div, RuntimeType.TYPE_NUMBER);
                } else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_CARET){
                if (checkBothNumberTypes(leftType, rightType)) {
                    double value = (Double) leftValue;
                    double exp = (Double) rightValue;
                    return new InterpreterResult(Math.pow(value, exp), RuntimeType.TYPE_NUMBER);
                } else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_GT){
                if (checkBothNumberTypes(leftType, rightType)) {
                    boolean value = (Double) leftValue >  (Double) rightValue;
                    return new InterpreterResult(value, RuntimeType.TYPE_BOOL);
                }
                // TODO String comparison
                else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_GE){
                if (checkBothNumberTypes(leftType, rightType)) {
                    boolean value = (Double) leftValue >=  (Double) rightValue;
                    return new InterpreterResult(value, RuntimeType.TYPE_BOOL);
                }
                // TODO String comparison
                else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_LT){
                if (checkBothNumberTypes(leftType, rightType)) {
                    boolean value = (Double) leftValue <  (Double) rightValue;
                    return new InterpreterResult(value, RuntimeType.TYPE_BOOL);
                }
                // TODO String comparison
                else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_LE){
                if (checkBothNumberTypes(leftType, rightType)) {
                    boolean value = (Double) leftValue <=  (Double) rightValue;
                    return new InterpreterResult(value, RuntimeType.TYPE_BOOL);
                }
                // TODO String comparison
                else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_EQEQ){
                if (checkBothNumberTypes(leftType, rightType)
                        || checkBothStringTypes(leftType, rightType)
                        || checkBothBoolTypes(leftType, rightType)) {
                    boolean value = leftValue.equals(rightValue);
                    return new InterpreterResult(value, RuntimeType.TYPE_BOOL);
                }
                else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
            else if (biOp.getOperator().getType() == TOK_NE){
                if (checkBothNumberTypes(leftType, rightType)
                        || checkBothStringTypes(leftType, rightType)
                        || checkBothBoolTypes(leftType, rightType)) {
                    boolean value = !leftValue.equals(rightValue);
                    return new InterpreterResult(value, RuntimeType.TYPE_BOOL);
                }
                else {
                    throw new InterpreterException("Unsupported operator: " + biOp.getOperator(), biOp.getLineNumber());
                }
            }
        }
        else if (node instanceof UnOpExpr unOp){
            InterpreterResult operand = interpret(unOp.getOperand());
            if (unOp.getOperator().getType() == TOK_PLUS){
                if (operand.getRuntimeType() == RuntimeType.TYPE_NUMBER){
                    double result = (double) operand.getValue();
                    return new InterpreterResult(result, RuntimeType.TYPE_NUMBER);
                } else {
                    // todo exception
                    throw new RuntimeException();
                }
            }
            else if (unOp.getOperator().getType() == TOK_MINUS){
                // if numerical value, negate it
                if (operand.getRuntimeType() == RuntimeType.TYPE_NUMBER){
                    double result = - (double) operand.getValue();
                    return new InterpreterResult(result, RuntimeType.TYPE_NUMBER);
                } else {
                    // todo exception
                    throw new RuntimeException();
                }
            }
            else if (unOp.getOperator().getType() == TOK_NOT){
                // if logical value, then negate it
                if (operand.getRuntimeType() == RuntimeType.TYPE_BOOL){
                    boolean result = !(boolean) operand.getValue();
                    return new InterpreterResult(result, RuntimeType.TYPE_BOOL);
                } else {
                    // todo exception
                    throw new RuntimeException();
                }
            }
        }
        else if (node instanceof LogicalOpExpr logicalOpExpr) {
            InterpreterResult leftResult = interpret(logicalOpExpr.getLeftOperand());
            Object leftValue = leftResult.getValue();
            if (logicalOpExpr.getOperator().getType() == TOK_OR){
                boolean result = (boolean) leftValue;
                if (result){
                    return new InterpreterResult(result, RuntimeType.TYPE_BOOL);
                }
            } else if (logicalOpExpr.getOperator().getType() == TOK_AND){
                boolean result = (boolean) leftValue;
                if (!result){
                    return new InterpreterResult(result, RuntimeType.TYPE_BOOL);
                }
            }
            return this.interpret(logicalOpExpr.getRightOperand());
        }
        else if (node instanceof StatementList stmtList) {
            for (Stmt stmt : stmtList.getStatements()) {
                this.interpret(stmt);
            }
        }
        else if (node instanceof PrintStmt printStmt) {
            InterpreterResult result = this.interpret(printStmt.getExpr());
            System.out.println(result.getValue());
        }
        else if (node instanceof IfStmt ifStmt) {
            InterpreterResult conditionResult = this.interpret(ifStmt.getCondition());
            if (conditionResult.getRuntimeType() != RuntimeType.TYPE_BOOL){
                throw new InterpreterException("Expecting boolean condition", ifStmt.getLineNumber());
            }
            boolean condition = (boolean) conditionResult.getValue();
            if (condition){
                this.interpret(ifStmt.getThenStmts());
            } else {
                if (ifStmt.getElseStmts() != null) {
                    this.interpret(ifStmt.getElseStmts());
                }
            }
        }
        return null;
    }

}
