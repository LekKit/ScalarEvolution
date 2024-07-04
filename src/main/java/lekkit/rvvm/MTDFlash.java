package lekkit.rvvm;

public class MTDFlash extends MMIODevice {
    public MTDFlash(RVVMMachine machine, String imagePath, boolean rw) {
        if (machine.isValid()) {
            this.machine = machine;
            this.mmio_handle = RVVMNative.mtd_physmap_init_auto(machine.machine, imagePath, rw);
        }
    }
}
