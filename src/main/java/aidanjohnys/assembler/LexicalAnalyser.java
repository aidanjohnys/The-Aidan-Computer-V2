package aidanjohnys.assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class LexicalAnalyser {
    public static ArrayList<Token> LexicalAnalyser(BufferedReader reader) throws IOException {
        ArrayList<Token> tokens = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");

            for (String word : words) {
                if (word.charAt(0) == ';') {
                    continue;
                }

            }
        }

        return tokens;
    }

    private static boolean isInstruction(String word) {
        return false;
    }
}
