package dev.mednikov.pinky;

import dev.mednikov.pinky.ast.Expr;
import dev.mednikov.pinky.ast.Node;
import dev.mednikov.pinky.interpreter.Interpreter;
import dev.mednikov.pinky.lexer.Lexer;
import dev.mednikov.pinky.lexer.Token;
import dev.mednikov.pinky.parser.Parser;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Pinky {

    public static void main(String[] args) {
        // Get script name from arguments
        if (args.length != 1) {
            System.out.println("Usage: java -jar pinky.jar <pinky-file>");
            System.exit(1);
        }
        String fileName = args[0];
        Path path = Path.of(fileName);
        // Read the file
        try {
            byte[] bytes = Files.readAllBytes(path);
            String src = new String(bytes, Charset.defaultCharset());
            // Source
            System.out.println("SOURCE");
            System.out.println(src);
            System.out.println("--------------------");

            // Lexer
            System.out.println("LEXER");
            Lexer lexer = new Lexer(src);
            List<Token> tokens = lexer.tokenize();
            for (Token token : tokens) {
                System.out.println(token);
            }
            System.out.println("--------------------");

            // Parser
            System.out.println("PARSER");
            Parser parser = new Parser(tokens);
            Node ast = parser.parse();
            System.out.println(ast.toString());
            System.out.println("--------------------");

            // Interpreter
            System.out.println("INTERPRETER");
            Interpreter interpreter = new Interpreter();
            interpreter.interpret(ast);
            System.out.println("--------------------");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
