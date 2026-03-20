package dev.mednikov.pinky.interpreter;

import dev.mednikov.pinky.ast.*;

import static dev.mednikov.pinky.lexer.TokenType.*;

public class Interpreter {

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
                if (leftType == RuntimeType.TYPE_NUMBER && rightType == RuntimeType.TYPE_NUMBER){
                    double sum = (Double) leftValue + (Double) rightValue;
                    return new InterpreterResult(sum, RuntimeType.TYPE_NUMBER);
                } else if (leftType == RuntimeType.TYPE_STRING || rightType == RuntimeType.TYPE_STRING){
                    String str = leftValue.toString() + rightValue.toString();
                    return new InterpreterResult(str, RuntimeType.TYPE_STRING);
                } else {
                    // todo throw exception
                    throw new RuntimeException();
                }
            }
            else if (biOp.getOperator().getType() == TOK_MINUS){
                if (leftType == RuntimeType.TYPE_NUMBER && rightType == RuntimeType.TYPE_NUMBER) {
                    double diff = (Double) leftValue - (Double) rightValue;
                    return new InterpreterResult(diff, RuntimeType.TYPE_NUMBER);
                } else {
                    // todo throw exception
                    throw new RuntimeException();
                }
            }
            else if (biOp.getOperator().getType() == TOK_STAR){
                // Multiply two numbers
                if (leftType == RuntimeType.TYPE_NUMBER && rightType == RuntimeType.TYPE_NUMBER) {
                    double result = (Double) leftValue * (Double) rightValue;
                    return new InterpreterResult(result, RuntimeType.TYPE_NUMBER);
                } else if (leftType == RuntimeType.TYPE_NUMBER || rightType == RuntimeType.TYPE_STRING){
                    // Repeat string
                    String str = rightValue.toString();
                    int count = (int) leftValue;
                    String result = str.repeat(count);
                    return new InterpreterResult(result, RuntimeType.TYPE_STRING);
                } else if (leftType == RuntimeType.TYPE_STRING || rightType == RuntimeType.TYPE_NUMBER){
                    // Repeat string
                    String str = leftValue.toString();
                    int count = (int) rightValue;
                    String result = str.repeat(count);
                    return new InterpreterResult(result, RuntimeType.TYPE_STRING);
                } else {
                    // todo throw exception
                    throw new RuntimeException();
                }
            }
            else if (biOp.getOperator().getType() == TOK_SLASH){
                if (leftType == RuntimeType.TYPE_NUMBER && rightType == RuntimeType.TYPE_NUMBER) {
                    double div = (Double) leftValue / (Double) rightValue;
                    return new InterpreterResult(div, RuntimeType.TYPE_NUMBER);
                } else {
                    // todo exception
                    throw new RuntimeException();
                }
            }
            else if (biOp.getOperator().getType() == TOK_MOD){
                if (leftType == RuntimeType.TYPE_NUMBER && rightType == RuntimeType.TYPE_NUMBER) {
                    double div = (Double) leftValue % (Double) rightValue;
                    return new InterpreterResult(div, RuntimeType.TYPE_NUMBER);
                } else {
                    // todo exception
                    throw new RuntimeException();
                }
            }
            else if (biOp.getOperator().getType() == TOK_CARET){
                if (leftType == RuntimeType.TYPE_NUMBER && rightType == RuntimeType.TYPE_NUMBER) {
                    double value = (Double) leftValue;
                    double exp = (Double) rightValue;
                    return new InterpreterResult(Math.pow(value, exp), RuntimeType.TYPE_NUMBER);
                } else {
                    // todo exception
                    throw new RuntimeException();
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
        return null;
    }

}
