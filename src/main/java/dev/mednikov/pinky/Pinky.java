package dev.mednikov.pinky;

import dev.mednikov.pinky.lexer.Lexer;
import dev.mednikov.pinky.lexer.Token;

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
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
