package aidanjohnys.Computer;

class Instructions {
    public static final byte HLT = 0x00;
    public static final byte ADD = 0x01;
    public static final byte ADI = 0x0C;
    public static final byte SUB = 0x02;
    public static final byte SBI = 0x0D;
    public static final byte STA = 0x03;
    public static final byte LDA = 0x04;
    public static final byte LDI = 0x05;
    public static final byte JMP = 0x09;
    public static final byte JPZ = 0x0A;
    public static final byte JNZ = 0x0B;
    public static final byte JCS = 0x0E;
    public static final byte JCC = 0x0F;
}
