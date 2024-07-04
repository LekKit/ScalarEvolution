package lekkit.rvvm;

public class Syscon extends MMIODevice {
    public Syscon(RVVMMachine machine) {
        if (machine.isValid()) {
            this.machine = machine;
            this.mmio_handle = RVVMNative.syscon_init_auto(machine.machine);
        }
    }
}
