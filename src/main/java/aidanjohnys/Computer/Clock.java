package aidanjohnys.Computer;

import java.util.concurrent.TimeUnit;

import static aidanjohnys.Computer.Computer.COMPUTER_STATUS_READY;

public class Clock {
    private final static int CLOCK_SPEED_HZ = 50;
    private final Computer computer;
    private long cycleCounter = 0;

    public Clock(Computer computer) {
        this.computer = computer;
    }

    public void tick() {
        while (computer.status == COMPUTER_STATUS_READY) {
            try {
                TimeUnit.SECONDS.sleep(1 / CLOCK_SPEED_HZ);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            computer.cycle();
            cycleCounter++;
        }
    }

}
