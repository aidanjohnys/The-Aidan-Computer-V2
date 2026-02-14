import aidanjohnys.Computer.Computer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static aidanjohnys.Computer.Instructions.*;

public class ControlUnitTest {
    @Test
    public void addTest() {
        Computer computer = new Computer();
        byte numA = 50;
        byte numB = 30;

        computer.loadProgramIntoMemory(new byte[] {ADD, 0x01, (byte) 0x00, numA});
        computer.accumulator = numB;
        computer.start();
        Assertions.assertEquals(80, computer.accumulator);
    }

    @Test
    public void addImmediateTest() {
        Computer computer = new Computer();
        byte numA = 50;
        byte numB = 30;

        computer.loadProgramIntoMemory(new byte[] {ADI, numA});
        computer.accumulator = numB;
        computer.start();
        Assertions.assertEquals(80, computer.accumulator);
    }

    @Test
    public void subtractTest() {
        Computer computer = new Computer();
        byte numA = 50;
        byte numB = 30;

        computer.loadProgramIntoMemory(new byte[] {SUB, 0x01, (byte) 0x00, numB});
        computer.accumulator = numA;
        computer.start();
        Assertions.assertEquals(20, computer.accumulator);
    }

    @Test
    public void subtractImmediateTest() {
        Computer computer = new Computer();
        byte numA = 50;
        byte numB = 30;

        computer.loadProgramIntoMemory(new byte[] {SBI, numB});
        computer.accumulator = numA;
        computer.start();
        Assertions.assertEquals(20, computer.accumulator);
    }

    @Test
    public void loadTest() {
        Computer computer = new Computer();
        byte numA = 50;

        computer.loadProgramIntoMemory(new byte[] {LDA, 0x01, (byte) 0x00, numA});
        computer.start();
        Assertions.assertEquals(50, computer.accumulator);
    }

    @Test
    public void loadImmediateTest() {
        Computer computer = new Computer();
        byte numA = 50;

        computer.loadProgramIntoMemory(new byte[] {LDI, numA});
        computer.start();
        Assertions.assertEquals(50, computer.accumulator);
    }
}
