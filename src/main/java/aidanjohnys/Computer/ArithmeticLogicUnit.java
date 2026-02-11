package aidanjohnys.Computer;

import static aidanjohnys.Computer.Instructions.*;

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
            case ADD:
            case ADI:
                carryBit = 0;
                computer.accumulator = addNum(computer.accumulator, (byte) computer.memoryDataRegister);
        }

    }

    private byte addNum(byte numA, byte numB) {
        byte[] aBits = new byte[Byte.SIZE];
        byte[] bBits = new byte[Byte.SIZE];
        byte[] outBits = new byte[Byte.SIZE];
        byte[] carry = new byte[Byte.SIZE];
        carry[0] = carryBit;

        for (int i = 0; i < Byte.SIZE; i++) {
            aBits[i] = (byte) ((numA >> i) & 1);
            bBits[i] = (byte) ((numB >> i) & 1);
        }

        for (int i = 0; i < Byte.SIZE; i++) {
            FullAdder fullAdder = new FullAdder();
            outBits[i] = fullAdder.operation(aBits[i], bBits[i], carry[i]);

            if (i < Byte.SIZE - 1) {
                carry[i + 1] = fullAdder.getCarryOut();
            }

            else {
                carryBit = fullAdder.getCarryOut();
            }
        }

        byte output = 0;
        for (int i = Byte.SIZE - 1; i >= 0; i--) {
            output += (byte) (outBits[i] << i);

        }

        return output;
    }
}
