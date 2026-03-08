package aidanjohnys.Assembler;


public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Usage: java Assembler <input> <output>");
        }

        final String input = args[0];
        final String output = args[1];

    }
}
