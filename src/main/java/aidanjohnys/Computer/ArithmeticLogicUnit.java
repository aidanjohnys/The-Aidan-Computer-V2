package aidanjohnys.Computer;

public class ArithmeticLogicUnit {
    private final Computer computer;
    public byte carryBit;

    public ArithmeticLogicUnit(Computer computer) {
        this.computer = computer;
        carryBit = 0;
    }

    public void performOperation() {
        byte instruction = (byte) ((computer.instructionRegister >> 8) & 0xFF);
        switch (instruction) {
            case 0x01:
            case 0x06:

        }

    }
}
