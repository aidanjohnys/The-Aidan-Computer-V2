package aidanjohnys.Computer;

import java.util.concurrent.TimeUnit;

public class Clock {
    private final static int CLOCK_SPEED_HZ = 50;
    private final Computer computer;
    private long cycleCounter = 0;

    public Clock(Computer computer) {
        this.computer = computer;
    }

    public void tick() {
        while (true) {
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
