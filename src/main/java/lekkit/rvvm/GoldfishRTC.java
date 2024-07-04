package lekkit.rvvm;

public class GoldfishRTC extends MMIODevice {
    public GoldfishRTC(RVVMMachine machine) {
        if (machine.isValid()) {
            this.machine = machine;
            this.mmio_handle = RVVMNative.rtc_goldfish_init_auto(machine.machine);
        }
    }
}
