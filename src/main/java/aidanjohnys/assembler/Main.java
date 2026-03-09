package aidanjohnys.assembler;


import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static aidanjohnys.assembler.LexicalAnalyser.LexicalAnalyser;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java assembler <input>");
            exit(0);
        }

        String fileHandle = args[0];
        Path path = Paths.get(fileHandle);
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            ArrayList<ArrayList<Token>> statements = LexicalAnalyser(reader);

            for (ArrayList<Token> tokens : statements) {
                for (Token token : tokens) {
                    System.out.print("(" + token.type.toString() + ", " + token.value + "), ");
                }

                System.out.println();
            }
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
