import aidanjohnys.Computer.Computer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static aidanjohnys.Computer.Instructions.*;

public class ArithmeticLogicUnitTest {
    @Test
    public void additionTest() {
        Computer computer = new Computer();

        // Test Basic Addition
        computer.instructionRegister = ADD << 8;
        computer.accumulator = 20;
        computer.memoryDataRegister = 15;
        computer.arithmeticLogicUnit.performOperation();
        Assertions.assertEquals(35, computer.accumulator);
        Assertions.assertEquals(0, computer.arithmeticLogicUnit.carryBit);

        computer.accumulator = (byte) 0xFF;
        computer.memoryDataRegister = 0;
        computer.arithmeticLogicUnit.performOperation();
        Assertions.assertEquals((byte) 0xFF, computer.accumulator);
        Assertions.assertEquals(0, computer.arithmeticLogicUnit.carryBit);

        // Overflow test
        computer.accumulator = (byte) 0xFF;
        computer.memoryDataRegister = 0xA0;
        computer.arithmeticLogicUnit.performOperation();
        Assertions.assertEquals((byte) 0x9F, computer.accumulator);
        Assertions.assertEquals(1, computer.arithmeticLogicUnit.carryBit);
    }

    @Test
    public void subtractionTest() {
        Computer computer = new Computer();

        // Test Basic Subtraction
        computer.instructionRegister = SUB << 8;
        computer.accumulator = 100;
        computer.memoryDataRegister = 25;
        computer.arithmeticLogicUnit.performOperation();
        Assertions.assertEquals(75, computer.accumulator);
        Assertions.assertEquals(1, computer.arithmeticLogicUnit.carryBit);

        // Test negative numbers
        computer.accumulator = 20;
        computer.memoryDataRegister = 25;
        computer.arithmeticLogicUnit.performOperation();
        Assertions.assertEquals((byte) -5, computer.accumulator);
        Assertions.assertEquals(0, computer.arithmeticLogicUnit.carryBit);

        // Test subtracting zero
        computer.accumulator = 77;
        computer.memoryDataRegister = 0;
        computer.arithmeticLogicUnit.performOperation();
        Assertions.assertEquals(77, computer.accumulator);
        Assertions.assertEquals(1, computer.arithmeticLogicUnit.carryBit);

        // Test zero minus positive number
        computer.accumulator = 0;
        computer.memoryDataRegister = 5;
        computer.arithmeticLogicUnit.performOperation();
        Assertions.assertEquals((byte) -5, computer.accumulator);
        Assertions.assertEquals(0, computer.arithmeticLogicUnit.carryBit);

        computer.accumulator = (byte) -65;
        computer.memoryDataRegister = (byte) -75;
        computer.arithmeticLogicUnit.performOperation();
        Assertions.assertEquals((byte) 10, computer.accumulator);
        Assertions.assertEquals(1, computer.arithmeticLogicUnit.carryBit);
    }
}
