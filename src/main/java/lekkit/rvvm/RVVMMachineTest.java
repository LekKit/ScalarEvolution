package lekkit.rvvm;

public class RVVMMachineTest {
    public static void main(String[] args) {
        RVVMNative.loadLib("/home/lekkit/RVVM/release.linux.x86_64/librvvm.so");
        RVVMMachine machine = new RVVMMachine(256, 1, true);
        new Framebuffer(machine, 640, 480);
        new PLIC(machine);
        new PCIBus(machine);
        new I2CBus(machine);
        new NS16550A(machine);
        new GoldfishRTC(machine);
        Syscon syscon = new Syscon(machine);
        new MTDFlash(machine, "/home/lekkit/stuff/vm/fw_jump.bin", false);
        machine.loadKernel("/home/lekkit/stuff/vm/Image");
        NVMeDrive drive = new NVMeDrive(machine, "/home/lekkit/stuff/vm/riscv64_arch.img", true);
        machine.appendCmdline("root=/dev/nvme0n1");

        HIDMouse mice = new HIDMouse(machine);
        mice.resolution(640, 480);


        SiFiveGPIO gpio = new SiFiveGPIO(machine);

        machine.start();
        //RVVMNative.run_eventloop();

        while (machine.isPowered()) {
            gpio.write_pins(0);
            try {
            Thread.sleep(1000);
            } catch (Throwable e) {}
            gpio.write_pins(1);
            if (gpio.read_pins() != 0) System.out.println("Pins up!");
            try {
            Thread.sleep(1000);
            } catch (Throwable e) {}
        }

        machine.finalize();
    }
}
