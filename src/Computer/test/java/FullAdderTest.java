import aidanjohnys.Computer.FullAdder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FullAdderTest {
    @Test
    public void fullAdderTest() {
        FullAdder f = new FullAdder();
        byte result = f.operation((byte) 0, (byte) 0, (byte) 0);
        Assertions.assertEquals((byte) 0, result);
        Assertions.assertEquals((byte) 0, f.getCarryOut());

        result = f.operation((byte) 1, (byte) 0, (byte) 0);
        Assertions.assertEquals((byte) 1, result);
        Assertions.assertEquals((byte) 0, f.getCarryOut());

        result = f.operation((byte) 1, (byte) 1, (byte) 0);
        Assertions.assertEquals((byte) 0, result);
        Assertions.assertEquals((byte) 1, f.getCarryOut());

        result = f.operation((byte) 1, (byte) 1, (byte) 1);
        Assertions.assertEquals((byte) 1, result);
        Assertions.assertEquals((byte) 1, f.getCarryOut());

    }
}
