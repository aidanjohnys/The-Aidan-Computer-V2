package aidanjohnys.assembler;

public class SyntaxError extends Exception {
    public SyntaxError(int lineNo, String word, TokenType expected) {
        super("Syntax Error at line " + lineNo + ": '" + word + "', expected <" + expected + ">.");
    }
}
