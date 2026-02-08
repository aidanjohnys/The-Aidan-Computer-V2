package aidanjohnys.Computer;

public class Memory implements AddressableMemory {
    private static final char CAPACITY_BYTES = 0x0800;
    private static final char LOCATION = 0x1000;
    private final byte[] memory;

    public Memory() {
        memory = new byte[CAPACITY_BYTES];
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
