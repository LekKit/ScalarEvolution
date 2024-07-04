package lekkit.rvvm;

public class MMIODevice {
    protected RVVMMachine machine;
    protected int mmio_handle;

    public void detach() {
        RVVMNative.detach_mmio(machine.machine, mmio_handle, true);
    }
}
