package aidanjohnys.Computer;

import static aidanjohnys.Computer.Computer.*;
import static aidanjohnys.Computer.Instructions.*;

public class ControlUnit {
    private final Computer computer;
    private int instructionCycleStep;
    private int fetchStep;
    private int executeStep;

    public ControlUnit(Computer computer) {
        this.computer = computer;
        instructionCycleStep = 0;
        fetchStep = 0;
        executeStep = 0;
    }

    public void cycle() {
        // Fetch
        if (instructionCycleStep == 0x00) {
            fetchInstruction();
        }

        // Execute
        if (instructionCycleStep == 0x01) {
            executeInstruction();
        }
    }

    private void fetchInstruction() {
        // Get next instruction from memory
        if (fetchStep == 0x00) {
            // Copy PC to MAR
            computer.memoryAddressRegister = computer.program_counter;

            // Copy MAR to Address Bus
            computer.addressBus = computer.memoryAddressRegister;

            // Send read signal over control bus
            computer.controlBus = CONTROL_READ;

            // Wait one cycle for data to come back
            fetchStep = 0x01;
        }

        // Receive instruction from memory
        else if (fetchStep == 0x01) {
            // Receive data from memory
            computer.memoryDataRegister = computer.dataBus;

            // Clear control signal
            computer.controlBus = CONTROL_CLEAR;

            // Copy MDR to IR
            computer.instructionRegister = computer.memoryDataRegister;

            // Increment program counter
            computer.program_counter++;

            // Reset fetch step
            fetchStep = 0x00;

            // Execute instruction on next cycle
            instructionCycleStep = 0x01;
        }

    }

    private void executeInstruction() {
        byte instruction = (byte) ((computer.instructionRegister >> 8) & 0xFF);

        switch (instruction) {
            // Halt
            case HLT:
                System.exit(0);
                break;

            // Add or Subtract (Zero Page)
            case ADD:
            case SUB:
                if (executeStep == 0x00) {
                    // Get the data from the memory location

                    // Copy memory address from instruction into MAR
                    computer.memoryAddressRegister = (byte) (computer.instructionRegister & 0xFF);

                    // Send read signal over control bus
                    computer.controlBus = CONTROL_READ;

                    // Put address on address bus
                    computer.addressBus = computer.memoryAddressRegister;

                    // Wait for data to come next cycle
                    executeStep = 0x01;

                }

                else if (executeStep == 0x01) {
                    // Receive data
                    computer.memoryDataRegister = (byte) (computer.dataBus & 0xFF);

                    // Clear control signal
                    computer.controlBus = CONTROL_CLEAR;

                    // ALU operation
                    computer.arithmeticLogicUnit.performOperation();

                    // Reset execute step
                    instructionCycleStep = 0x00;
                    executeStep = 0x00;
                }
                break;

            // Add or Subtract (Immediate)
            case ADI:
            case SBI:
                // Put the number on the MDR
                computer.memoryDataRegister = (byte) (computer.instructionRegister & 0xFF);

                // ALU operation
                computer.arithmeticLogicUnit.performOperation();
                break;

            // Store Accumulator
            case STA:
                if (executeStep == 0x00) {
                    // Copy accumulator value to MDR
                    computer.memoryDataRegister = computer.accumulator;

                    // Put data on data bus
                    computer.dataBus = computer.memoryAddressRegister;

                    // Put address on address bus
                    computer.addressBus = (byte) (computer.instructionRegister & 0xFF);

                    // Send write signal over control bus
                    computer.controlBus = CONTROL_WRITE;

                    // Wait for data to be written
                    executeStep = 0x01;
                }

                else if (executeStep == 0x01) {
                    // Clear control signal
                    computer.controlBus = CONTROL_CLEAR;

                    // Reset execute step
                    instructionCycleStep = 0x00;
                    executeStep = 0x00;
                }
                break;

            // Load Accumulator
            case LDA:
                if (executeStep == 0x00) {
                    // Get the data from memory

                    // Copy memory address from IR into MAR
                    computer.memoryAddressRegister = (byte) (computer.instructionRegister & 0xFF);

                    // Send read signal over control bus
                    computer.controlBus = CONTROL_READ;

                    // Put address on address bus
                    computer.addressBus = computer.memoryAddressRegister;

                    executeStep = 0x01;
                }

                else if (executeStep == 0x01) {
                    // Receive data from memory
                    computer.memoryDataRegister = (byte) (computer.dataBus & 0xFF);

                    // Clear control signal
                    computer.controlBus = CONTROL_CLEAR;

                    // Copy to accumulator
                    computer.accumulator = (byte) computer.memoryDataRegister;

                    // Reset execute step
                    instructionCycleStep = 0x00;
                    executeStep = 0x00;
                }
                break;

            // Load Accumulator (Immediate)
            case LDI:
                computer.accumulator = (byte) (computer.instructionRegister & 0xFF);

                // Reset execute step
                instructionCycleStep = 0x00;
                executeStep = 0x00;
                break;

            // Jump (Unconditional)
            case JMP:
                // Set program counter to address given
               computer.program_counter = (byte) (computer.instructionRegister & 0xFF);

                // Reset execute step
                instructionCycleStep = 0x00;
                executeStep = 0x00;
                break;

            // Jump if zero
            case JPZ:
                // Check if accumulator is equal to zero
                if (computer.accumulator == 0x00) {
                    // Set program counter to address given
                    computer.program_counter = (byte) (computer.instructionRegister & 0xFF);
                }

                // Reset execute step
                instructionCycleStep = 0x00;
                executeStep = 0x00;
                break;

            // Jump if not zero
            case JNZ:
                // Check if accumulator is not equal to zero
                if (computer.accumulator != 0x00) {
                    // Set program counter to address given
                    computer.program_counter = (byte) (computer.instructionRegister & 0xFF);
                }

                // Reset execute step
                instructionCycleStep = 0x00;
                executeStep = 0x00;
                break;

            // Jump if carry is set
            case JCS:
                // Check if ALU carry bit is set
                if (computer.arithmeticLogicUnit.carryBit == 0x01) {
                    // Set program counter to address given
                    computer.program_counter = (byte) (computer.instructionRegister & 0xFF);
                }

                // Reset execute step
                instructionCycleStep = 0x00;
                executeStep = 0x00;
                break;

            // Jump if carry is clear
            case JCC:
                // Check if ALU carry bit is clear
                if (computer.arithmeticLogicUnit.carryBit == 0x00) {
                    // Set program counter to address given
                    computer.program_counter = (byte) (computer.instructionRegister & 0xFF);
                }

                // Reset execute step
                instructionCycleStep = 0x00;
                executeStep = 0x00;
                break;

            default:
                throw new IllegalStateException("An illegal opcode has been used, stopping now.");
        }
    }
}
