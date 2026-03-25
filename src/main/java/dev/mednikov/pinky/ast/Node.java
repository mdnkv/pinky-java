package dev.mednikov.pinky.ast;

public class Node {

    private final int lineNumber;

    public Node(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
