package lekkit.rvvm;

public class RVVMMachineTest {
    public static void main(String[] args) {
        RVVMMachine machine = new RVVMMachine(256, 1, true);
        new PLIC(machine);
        new PCIBus(machine);
        new I2CBus(machine);
        new NS16550A(machine);
        new GoldfishRTC(machine);
        new Syscon(machine);
        new MTDFlash(machine, "/home/lekkit/stuff/vm/fw_payload.bin", false);
        NVMeDrive drive = new NVMeDrive(machine, "/home/lekkit/stuff/vm/FreeBSD-13.2-RELEASE-riscv-riscv64-GENERICSD.img", true);

        HIDMouse mice = new HIDMouse(machine);
        mice.resolution(640, 480);

        machine.start();
        RVVMNative.run_eventloop();
    }
}
