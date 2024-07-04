package lekkit.rvvm;

public class PCIBus {
    protected final RVVMMachine machine;
    protected final long pci_bus;

    public PCIBus(RVVMMachine machine) {
        if (machine.isValid()) {
            this.machine = machine;
            pci_bus = RVVMNative.pci_bus_init_auto(machine.machine);
        } else {
            this.machine = null;
            pci_bus = 0;
        }
    }
}
