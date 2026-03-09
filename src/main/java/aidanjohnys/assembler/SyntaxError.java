package aidanjohnys.assembler;

public class SyntaxError extends Exception {
    public SyntaxError(int lineNo, Token token) {
        super("Syntax Error at line " + lineNo + ": " + token.value);
    }
}
