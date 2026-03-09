package aidanjohnys.assembler;

public class Token {
    public TokenType type;
    public TokenType specificType;
    public String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type, TokenType specificType, String value) {
        this.type = type;
        this.value = value;
        this.specificType = specificType;
    }
}
