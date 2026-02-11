package aidanjohnys;

import aidanjohnys.Computer.Computer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer();

        if (args.length < 1) {
            throw new RuntimeException("You need to pass a file handle as an argument to load the software into memory.");
        }

        try {
            computer.loadProgramIntoMemory(args[0]);
        }

        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + args[0]);
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

        computer.start();
    }
}