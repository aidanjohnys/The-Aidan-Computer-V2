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

        computer.loadProgramIntoMemory(new byte[] {ADD, 0x02, (byte) 0x00, numA});
        computer.instructionRegister = ADD << 8;

        computer.accumulator = numB;
        computer.start();
        Assertions.assertEquals(80, computer.accumulator);
    }
}
