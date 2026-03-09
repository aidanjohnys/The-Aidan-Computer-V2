package aidanjohnys.assembler;


import java.io.BufferedReader;
import java.io.IOException;
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
            ArrayList<Token> tokens = LexicalAnalyser(reader);

            for (Token token : tokens) {
                System.out.println(token.type.toString() + ", " + token.value + "\n");
            }
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
