package aidanjohnys.Computer;

public class Computer {
    // Registers

    private byte program_counter = 0;
    private byte accumulator = 0;
    private byte instruction_register = 0;
    private byte memory_address_register = 0;
    private byte memory_data_register = 0;

    // Buses
    public char data_bus = 0;
    public byte address_bus = 0;
    public byte control_bus = 0;

    // Units
    private final ArithmeticLogicUnit arithmeticLogicUnit;
    private final ControlUnit controlUnit;
    private final Memory memory;
    private final IO io;
    private final Clock clock;

    public Computer() {
        arithmeticLogicUnit = new ArithmeticLogicUnit();
        controlUnit = new ControlUnit();
        memory = new Memory();
        io = new IO();
        clock = new Clock(this);

        clock.tick();
    }

    public void cycle() {

    }
}
