package aidanjohnys.Computer;


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Computer {
    public static final byte CONTROL_CLEAR = 0x00;
    public static final byte CONTROL_READ = 0x01;
    public static final byte CONTROL_WRITE = 0x02;
    public static final byte COMPUTER_STATUS_READY = 0x00;
    public static final byte COMPUTER_STATUS_HALT = 0x01;
    public static final byte COMPUTER_STATUS_ILLEGAL_OPERATION = 0x02;

    // Registers
    public byte program_counter = 0;
    public byte accumulator = 0;
    public short instructionRegister = 0;
    public byte memoryAddressRegister = 0;
    public short memoryDataRegister = 0;
    public byte status = COMPUTER_STATUS_READY;

    // Buses
    public short dataBus = 0;
    public byte addressBus = 0;
    public byte controlBus = 0;

    // Units
    public final ArithmeticLogicUnit arithmeticLogicUnit;
    private final ControlUnit controlUnit;
    private final Memory memory;
    private final IO io;
    private final Clock clock;

    public Computer() {
        arithmeticLogicUnit = new ArithmeticLogicUnit(this);
        controlUnit = new ControlUnit(this);
        memory = new Memory(this);
        io = new IO(this);
        clock = new Clock(this);
    }

    public void cycle() {
        controlUnit.cycle();
        memory.cycle();
        io.cycle();
    }

    public void start() {
        clock.tick();
    }

    public void loadProgramIntoMemory(String fileHandle) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(fileHandle)) {
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            memory.loadIntoMemory(dataInputStream.readAllBytes());
        }
    }

    public void loadProgramIntoMemory(byte[] bytes) {
        memory.loadIntoMemory(bytes);
    }
}
