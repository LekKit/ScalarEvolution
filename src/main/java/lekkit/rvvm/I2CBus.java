package lekkit.rvvm;

public class I2CBus {
    protected final RVVMMachine machine;
    protected final long i2c_bus;

    public I2CBus(RVVMMachine machine) {
        if (machine.isValid()) {
            this.machine = machine;
            i2c_bus = RVVMNative.i2c_bus_init_auto(machine.machine);
        } else {
            this.machine = null;
            i2c_bus = 0;
        }
    }
}
