package lekkit.rvvm;

public class PCIDevice {
    protected RVVMMachine machine;
    protected long pci_dev;

    public void detach() {
        RVVMNative.pci_remove_device(pci_dev);
    }
}
