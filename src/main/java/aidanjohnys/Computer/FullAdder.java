package aidanjohnys.Computer;

public class FullAdder {
    private byte carryOut;

    public void FullAdder() {
        carryOut = 0;
    }

    public byte operation(byte a, byte b, byte carryIn) {
        byte sum = (byte) (a ^ b ^ carryIn);
        carryOut = (byte) (a & b | carryIn & (a ^ b));
        return sum;
    }

    public byte getCarryOut() {
        return carryOut;
    }
}
