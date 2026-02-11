package aidanjohnys.Computer;

import static aidanjohnys.Computer.Computer.*;

public class Memory implements AddressableMemory {
    private static final char CAPACITY_BYTES = 0x254;
    private static final char LOCATION = 0x0000;
    private final byte[] memory;
    private final Computer computer;

    public Memory(Computer computer) {
        this.computer = computer;
        memory = new byte[CAPACITY_BYTES];
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

    public void write() {
        byte address = computer.addressBus;
        // Todo: add range check
        memory[address] = (byte) computer.dataBus;
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
