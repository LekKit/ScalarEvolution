package lekkit.scev.server;

import java.util.HashMap;
import java.util.UUID;
import lekkit.rvvm.*;

public class MachineState {
    private UUID uuid;

    private RVVMMachine machine = null;
    private Framebuffer display = null;
    private HIDMouse mouse = null;
    private HIDKeyboard keyboard = null;
    private MTDFlash flash = null;
    private RTL8169 nic = null;
    private SiFiveGPIO gpio = null;

    private HashMap<UUID, NVMeDrive> nvme_drives = new HashMap<UUID, NVMeDrive>();

    private boolean paused = false;
    private boolean unloaded = false;
    private boolean persisting = true;

    Object opaque = null;

    public MachineState(UUID machineUUID) {
        uuid = machineUUID;
    }

    public boolean create(long mem_mb, int smp, boolean rv64) {
        RVVMNative.loadLib("/usr/lib/librvvm.so");

        machine = new RVVMMachine(mem_mb, smp, rv64);
        if (machine.isValid()) {
            machine.setOption(RVVMMachine.RVVM_OPT_HW_IMITATE, 1);

            machine.appendCmdline("root=/dev/nvme0n1 rw");

            new PLIC(machine);
            new PCIBus(machine);
            new I2CBus(machine);
            new GoldfishRTC(machine);
            new Syscon(machine);
            new NS16550A(machine);

            keyboard = new HIDKeyboard(machine);
            mouse = new HIDMouse(machine);
            mouse.resolution(640, 480);
        }

        return machine.isValid();
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setOpaque(Object opaque) {
        this.opaque = opaque;
    }

    public Object getOpaque() {
        return opaque;
    }

    public void setPersisting(boolean persisting) {
        this.persisting = persisting;
    }

    public boolean isPersisting() {
        return persisting;
    }

    public boolean loadSnapshot() {
        // TODO: Load machine snapshot by UUID, remove it from disk
        return false;
    }

    public void saveSnapshot() {
        if (isPersisting()) {
            // TODO: Save machine snapshot by UUID
        }
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
            flash.remove();
            flash = null;
        }
    }

    public void pullVideoAdapter() {
        if (display != null) {
            display.remove();
            display = null;
        }
    }

    public void pullNetworkingCard() {
        if (nic != null) {
            nic.remove();
            nic = null;
        }
    }

    public void pullNVMe(UUID disk_uuid) {
        NVMeDrive nvme = nvme_drives.get(disk_uuid);
        if (nvme != null) {
            nvme.remove();
            nvme_drives.remove(disk_uuid);
        }
    }

    public void pullGPIO() {
        if (gpio != null) {
            gpio.remove();
            gpio = null;
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

    public SiFiveGPIO getGPIO() {
        return gpio;
    }

    public void tryResume() {
        if (!unloaded && !paused && machine.isPowered()) {
            machine.start();
        }
    }

    public void unload() {
        if (!unloaded) {
            unloaded = true;
            machine.pause();
        }
    }

    public void load() {
        if (unloaded) {
            unloaded = true;
            tryResume();
        }
    }

    public void pause() {
        if (!paused) {
            paused = true;
            machine.pause();
        }
    }

    public void unpause() {
        if (paused) {
            paused = false;
            tryResume();
        }
    }

    public synchronized void destroy() {
        if (machine != null) {
            machine.pause();
            machine.free();
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
