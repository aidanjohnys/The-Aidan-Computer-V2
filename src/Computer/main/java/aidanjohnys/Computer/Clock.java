package aidanjohnys.Computer;

import java.util.concurrent.TimeUnit;

import static aidanjohnys.Computer.Computer.COMPUTER_STATUS_READY;

public class Clock {
    private final static float CLOCK_SPEED_HZ = 1000;
    private final Computer computer;
    private long cycleCounter = 0;

    public Clock(Computer computer) {
        this.computer = computer;
    }

    public void tick() {
        while (computer.status == COMPUTER_STATUS_READY) {
            try {
                TimeUnit.MICROSECONDS.sleep((long) (1f / CLOCK_SPEED_HZ * 1_000_000f));
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            computer.cycle();
            cycleCounter++;
        }
    }

}
