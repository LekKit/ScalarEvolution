package lekkit.rvvm;

public class RTL8169 extends PCIDevice {
    public RTL8169(RVVMMachine machine) {
        if (machine.isValid()) {
            this.machine = machine;
            this.pci_dev = RVVMNative.rtl8169_init_auto(machine.machine);
        }
    }
}
