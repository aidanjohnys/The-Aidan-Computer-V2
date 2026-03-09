package aidanjohnys.assembler;

import aidanjohnys.computer.Instructions;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static aidanjohnys.assembler.TokenType.*;

public class LexicalAnalyser {
    private static final TokenType[] expectedTokensForStmt = {label, instruction, argument, comment};
    private static final TokenType[] expectedTokensForMacro = {identifier, assignment, constant, comment};
    private static final TokenType[] expectedTokensForVariable = {variableInitialisation, variableInitialisationValue, comment};

    public static ArrayList<ArrayList<Token>> LexicalAnalyser(BufferedReader reader) throws IOException, SyntaxError {
        ArrayList<ArrayList<Token>> statements = new ArrayList<>();

        String line;
        int lineNo = 1;
        while ((line = reader.readLine()) != null) {
            ArrayList<Token> statement = new ArrayList<>();
            String[] words = line.split(" ");
            int currentExpectedToken = 0;
            TokenType[] expectedTokens = expectedTokensForStmt;

            for (String word : words) {
                word = word.toUpperCase();
                if (word.equalsIgnoreCase("")) {
                    continue;
                }

                Token token = null;
                while (true) {
                    TokenType expectedToken = expectedTokens[currentExpectedToken];
                    if (expectedToken == comment) {
                        break;
                    }

                    token = tokenAnalyser(word, expectedToken);

                    if (token == null) {
                        // Label not required
                        if (expectedToken == label) {
                            currentExpectedToken++;
                            continue;
                        }

                        // Not a macro either so check for a variable initialisation
                        if (expectedToken == instruction) {
                            currentExpectedToken = 0;
                            expectedTokens = expectedTokensForVariable;
                            continue;
                        }

                        // No instruction here? Could be a macro definition instead
                        if (expectedToken == variableInitialisation) {
                            expectedTokens = expectedTokensForMacro;
                            continue;
                        }

                        throw new SyntaxError(lineNo, word, expectedToken);
                    }

                    else if (token.type != expectedToken) {
                        // Has found a token but is the wrong token, eg:
                        // the argument given for an instruction is the equals sign,
                        // only a comment as the first token can break this rule
                        if (token.type == comment && currentExpectedToken == 0) {
                            break;
                        }

                        throw new SyntaxError(lineNo, word, expectedToken);

                    }

                    statement.add(token);
                    currentExpectedToken++;
                    break;
                }

                if (token == null || token.type == comment) {
                    break;
                }

            }

            if (!statement.isEmpty()) {
                statements.add(statement);
            }
            lineNo++;

        }

        return statements;
    }

    private static Token tokenAnalyser(String word, TokenType expected) {
        final Set<Character> numChars = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        final Set<Character> hexChars = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');

        switch (expected) {
            case label:
                // todo: eventually remove the requirement for the dot at the start
                if (word.charAt(word.length() - 1) == ':' && word.charAt(0) == '.') {
                    return new Token(label, word);
                }

                if (word.charAt(0) == ';') {
                    return new Token(comment, word);
                }

                break;

            case instruction:
                if (isInstruction(word)) {
                    return new Token(instruction, word);
                }

                break;

            case identifier:
                return new Token(identifier, word);

            case assignment:
                if (word.equalsIgnoreCase("=")) {
                    return new Token(assignment, word);
                }
                break;

            case constant:
                if (word.length() <= 1) {
                    return null;
                }

                if (word.charAt(0) == '#') {
                    if (word.charAt(1) == '$') {
                        for (int i = 2; i < word.length(); i++) {
                            if (!hexChars.contains(word.charAt(i))) {
                                return null;
                            }
                        }

                        return new Token(constant, hexConstant, word);
                    }

                    if (word.charAt(1) == '\'' && word.charAt(3) == '\'' && word.length() == 4) {
                        return new Token(constant, charConstant, word);
                    }

                    for (int i = 1; i < word.length(); i++) {
                        if (!numChars.contains(word.charAt(i))) {
                            return null;
                        }
                    }

                    return new Token(constant, decimalConstant, word);
                }

                else if (word.charAt(0) == '$') {
                    for (int i = 1; i < word.length(); i++) {
                        if (!hexChars.contains(word.charAt(i))) {
                            return null;
                        }
                    }

                    return new Token(constant, hexAddress, word);
                }

                return null;

            case variableInitialisation:
                if (word.charAt(word.length() - 1) == ':') {
                    return new Token(variableInitialisation, word);
                }

                return null;

            case variableInitialisationValue:
                if (word.charAt(0) == '\'' && word.charAt(2) == '\'' && word.length() == 3) {
                    return new Token(variableInitialisationValue, charConstant, word);
                }

                if (word.charAt(0) == '$') {
                    for (int i = 2; i < word.length(); i++) {
                        if (!hexChars.contains(word.charAt(i))) {
                            return null;
                        }
                    }

                    return new Token(variableInitialisationValue, hexConstant, word);
                }

                for (int i = 1; i < word.length(); i++) {
                    if (!numChars.contains(word.charAt(i))) {
                        return null;
                    }
                }

                return new Token(variableInitialisationValue, decimalConstant, word);

            case argument:
                return new Token(argument, word);
        }

        return null;
    }

    private static boolean isInstruction(String word) {
        for (String mnemonic : Instructions.getMnemonics())
            if (mnemonic.equalsIgnoreCase(word)) {
                return true;
            }

        return false;
    }
}
