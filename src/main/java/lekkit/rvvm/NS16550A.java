package lekkit.rvvm;

public class NS16550A extends MMIODevice {
    public NS16550A(RVVMMachine machine) {
        if (machine.isValid()) {
            this.machine = machine;
            this.mmio_handle = RVVMNative.ns16550a_init_auto(machine.machine);
        }
    }
}
