package lekkit.rvvm;

public class NVMeDrive extends PCIDevice {
    public NVMeDrive(RVVMMachine machine, String imagePath, boolean rw) {
        if (machine.isValid()) {
            this.machine = machine;
            this.pci_dev = RVVMNative.nvme_init_auto(machine.machine, imagePath, rw);
        }
    }
}
