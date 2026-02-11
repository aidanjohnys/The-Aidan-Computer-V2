package aidanjohnys.Computer;

import java.io.IOException;

import static aidanjohnys.Computer.Computer.*;

public class IO implements AddressableMemory {
    private final Computer computer;
    private byte buffer;
    private byte status;
    private static final byte IO_IDLE = 0;
    private static final byte IO_INPUT = 1;
    private static final byte IO_OUTPUT = 2;
    private static final byte IO_BUFFER_LOCATION = (byte) 0xFE;
    private static final byte IO_STATUS_LOCATION = (byte) 0xFF;

    public IO(Computer computer) {
        this.computer = computer;
        buffer = 0;
        status = CONTROL_CLEAR;
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

        switch (status) {
            case IO_IDLE:
                break;

            case IO_INPUT:
                getCharacter();
                break;

            case IO_OUTPUT:
                printCharacter();
                break;
        }
    }

    public void read() {
        switch (computer.addressBus) {
            // Put IO buffer onto data bus
            case IO_BUFFER_LOCATION:
                computer.dataBus = buffer;
                break;

            // Put IO status onto data bus
            case IO_STATUS_LOCATION:
                computer.dataBus = status;
                break;
        }
    }

    public void write() {
        switch (computer.addressBus) {
            // Copy data into the buffer
            case IO_BUFFER_LOCATION:
                buffer = (byte) computer.dataBus;
                break;

            // Update IO status
            case IO_STATUS_LOCATION:
                status = (byte) computer.dataBus;
        }
    }

    private void getCharacter() {
        try {
            buffer = (byte) System.in.read();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

        status = IO_IDLE;
    }

    private void printCharacter() {
        System.out.print(buffer);
        status = IO_IDLE;
    }

    @Override
    public char location() {
        return 0xFE;
    }

    @Override
    public char size() {
        return 0x02;
    }
}
