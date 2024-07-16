package lekkit.scev.server;

import java.util.UUID;
import java.util.HashMap;

import lekkit.rvvm.*;

public class MachineState {
    private UUID uuid;

    private RVVMMachine machine = null;
    private Framebuffer display = null;
    private HIDMouse mouse = null;
    private HIDKeyboard keyboard = null;
    private MTDFlash flash = null;
    private RTL8169 nic = null;
    private GPIODevice gpio = null;

    public HashMap<UUID, NVMeDrive> nvme_drives = new HashMap<UUID, NVMeDrive>();

    public MachineState(UUID machine_uuid) {
        uuid = machine_uuid;
    }

    public boolean create(long mem_mb, int smp, boolean rv64) {
        RVVMNative.loadLib("/usr/lib/librvvm.so");

        machine = new RVVMMachine(mem_mb, smp, rv64);
        if (machine.isValid()) {
            new PLIC(machine);
            new PCIBus(machine);
            new I2CBus(machine);
            new GoldfishRTC(machine);
            new Syscon(machine);

            keyboard = new HIDKeyboard(machine);
            mouse = new HIDMouse(machine);
        }

        return machine.isValid();
    }

    public boolean attachFirmwareFlash(UUID disk_uuid, long disk_mb, String origin) {
        if (machine != null && machine.isValid() && flash == null) {
            if (StorageManager.initImage(disk_uuid, disk_mb, origin)) {
                flash = new MTDFlash(machine, StorageManager.imagePath(disk_uuid), true);
                return true;
            }
        }
        return false;
    }

    public boolean attachVideoAdapter() {
        if (machine != null && machine.isValid() && display == null) {
            display = new Framebuffer(machine, 640, 480, 32);
            return true;
        }
        return false;
    }

    public boolean attachNetworkingCard() {
        if (machine != null && machine.isValid() && nic == null) {
            nic = new RTL8169(machine);
            return true;
        }
        return false;
    }

    public boolean attachNVMeDrive(UUID disk_uuid, long disk_mb, String origin) {
        if (nvme_drives.get(disk_uuid) == null) {
            if (StorageManager.initImage(disk_uuid, disk_mb, origin)) {
                NVMeDrive nvme = new NVMeDrive(machine, StorageManager.imagePath(disk_uuid), true);
                nvme_drives.put(disk_uuid, nvme);
                return true;
            }
        }
        return false;
    }

    public boolean attachGPIO() {
        if (machine != null && machine.isValid() && gpio == null) {
            gpio = new SiFiveGPIO(machine);
            return true;
        }
        return false;
    }

    public void pullFirmwareFlash() {
        if (flash != null) {
            flash.detach();
        }
    }

    public void pullVideoAdapter() {
        if (display != null) {
            display.detach();
        }
    }

    public void pullNetworkingCard() {
        if (nic != null) {
            nic.detach();
        }
    }

    public void pullNVMe(UUID disk_uuid) {
        NVMeDrive nvme = nvme_drives.get(disk_uuid);
        if (nvme != null) {
            nvme.detach();
            nvme_drives.remove(disk_uuid);
        }
    }

    public void pullGPIO() {
        if (gpio != null) {
            gpio.detach();
        }
    }

    public RVVMMachine getMachine() {
        return machine;
    }

    public Framebuffer getDisplay() {
        return display;
    }

    public HIDMouse getMouse() {
        return mouse;
    }

    public HIDKeyboard getKeyboard() {
        return keyboard;
    }

    public GPIODevice getGPIO() {
        return gpio;
    }

    public void destroy() {
        if (machine != null) {
            machine.pause();
            machine.dumpContext();
        }

        machine = null;
        display = null;
        mouse = null;
        keyboard = null;
        flash = null;
        nic = null;
        gpio = null;
    }
}
