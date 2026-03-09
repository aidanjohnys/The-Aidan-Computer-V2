package aidanjohnys.assembler;

import aidanjohnys.computer.Instructions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class LexicalAnalyser {
    public static ArrayList<Token> LexicalAnalyser(BufferedReader reader) throws IOException, SyntaxError {
        ArrayList<Token> tokens = new ArrayList<>();

        String line;
        int lineNo = 1;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");

            for (String word : words) {
                Token label = null;
                Token instruction = null;
                Token argument = null;

                Token token = null;



                if (word.equalsIgnoreCase("")) {
                    continue;
                }

                // Label
                else if (word.charAt(word.length() - 1) == ':') {
                    token = new Token(TokenType.label, word);
                }

                // Some kind of constant
                else if (word.charAt(0) == '#') {
                    if (word.charAt(1) == '$') {
                        token = new Token(TokenType.decimalConstant, word);
                    }

                    else if (word.charAt(1) == '\'') {
                        if (word.length() != 4 || word.charAt(3) != '\'') {
                            token = new Token(TokenType.unknown, word);
                        }

                        else {
                            token = new Token(TokenType.charConstant, word);
                        }
                    }
                }

                // Comment
                else if (word.charAt(0) == ';') {
                    break;
                }

                else if (isInstruction(word)) {
                    token = new Token(TokenType.instruction, word);
                }

                else {
                    token = new Token(TokenType.identifier, word);
                }

//                if (token.type == TokenType.unknown) {
//                    throw new SyntaxError(lineNo, token);
//                }

                tokens.add(token);
            }

            lineNo++;
        }

        return tokens;
    }

    private static boolean isInstruction(String word) {
        for (String mnemonic : Instructions.getMnemonics())
            if (mnemonic.equalsIgnoreCase(word)) {
                return true;
            }

        return false;
    }
}
