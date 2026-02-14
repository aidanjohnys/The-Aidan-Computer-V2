package aidanjohnys.Computer;

import static aidanjohnys.Computer.Computer.*;

public class Memory implements AddressableMemory {
    private static final char CAPACITY_BYTES = 254;
    private static final char LOCATION = 0;
    private final short[] memory;
    private final Computer computer;

    public Memory(Computer computer) {
        this.computer = computer;
        memory = new short[CAPACITY_BYTES / 2];
    }

    public void cycle() {
        switch (computer.controlBus) {
            case CONTROL_CLEAR:
                break;

            case CONTROL_READ:
                read();
                break;

            case CONTROL_WRITE:
                write();
                break;
        }
    }

    private void read() {
        byte address = computer.addressBus;
        // Todo: add range check
        computer.dataBus = memory[address];
    }

    private void write() {
        byte address = computer.addressBus;
        // Todo: add range check
        memory[address] = computer.dataBus;
    }

    public void loadIntoMemory(byte[] bytes) {
        for (int i = 0; i < bytes.length; i += 2) {
            if (i >= CAPACITY_BYTES) {
                System.err.println("Warning: loaded program is larger than memory size! Stopped reading.");
                return;
            }

            memory[i / 2] = (short) (bytes[i] << 8);
            memory[i / 2] += bytes[i + 1];
        }
    }

    public short[] getMemory() {
        return memory;
    }

    @Override
    public char location() {
        return LOCATION;
    }

    @Override
    public char size() {
        return CAPACITY_BYTES;
    }
}
