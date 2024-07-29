package lekkit.scev.tileentity;

import java.util.UUID;
import lekkit.scev.packet.PacketDispatcher;
import lekkit.scev.packet.client.DisplayPacket;
import lekkit.scev.server.IMachineHandle;
import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileEntityComputer extends TileEntityBaseInventory implements IMachineHandle {
    protected UUID machineUUID;
    protected boolean unloaded = false;

    public TileEntityComputer(int invSize) {
        super(invSize);

        machineUUID = UUID.randomUUID();
    }

    public UUID getMachineUUID() {
        return machineUUID;
    }

    public boolean runningOnServer() {
        // For whatever reason worldObj is null on world load
        return getWorldObj() == null || !getWorldObj().isRemote;
    }

    public boolean isLaptop() {
        return false;
    }

    protected MachineState buildMachine(UUID uuid) {
        return null;
    }

    protected MachineState initMachineState() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());

        if (state == null) {
            state = buildMachine(getMachineUUID());
        }

        return state;
    }

    public void powerOn() {
        MachineState state = initMachineState();

        if (state != null) {
            state.getMachine().start();
        }
    }

    public void powerOff() {
        MachineManager.destroyMachineState(getMachineUUID());
    }

    public void unload() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());

        if (state != null) {
            state.unload();
            unloaded = true;
        }
    }

    public void resume() {
        if (unloaded) {
            MachineState state = MachineManager.getMachineState(getMachineUUID());

            if (state != null) {
                state.load();
            }
        }
        unloaded = false;
    }

    public boolean isPowered() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());
        if (state != null) {
            return state.getMachine().isPowered();
        }
        return false;
    }

    public boolean isUnloaded() {
        return unloaded;
    }

    public void power() {
        if (isPowered()) {
            powerOff();
        } else {
            powerOn();
        }
    }

    public void reset() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());
        if (state != null) {
            state.getMachine().reset();
        }
    }

    @Override
    public void deserializeFromNBT(NBTTagCompound compound) {
        super.deserializeFromNBT(compound);

        try {
            String uuidString = compound.getString("UUID");
            if (uuidString != null) {
                machineUUID = UUID.fromString(uuidString);

                if (runningOnServer()) {
                    if (MachineManager.hasMachineStateSnapshot(machineUUID)) {
                        MachineState state = initMachineState();

                        if (state != null) {
                            state.loadSnapshot();
                            state.getMachine().start();
                        }
                    }
                }
            }
        } catch (Throwable e) {}
    }

    @Override
    public void serializeToNBT(NBTTagCompound compound) {
        super.serializeToNBT(compound);
        compound.setString("UUID", getMachineUUID().toString());
    }

    @Override
    public void invalidate() {
        super.invalidate();

        if (isLaptop() && runningOnServer()) {
            // Kill the running machine
            powerOff();
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();

        if (runningOnServer()) {
            // Unload the running machine
            unload();
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (runningOnServer()) {
            MachineState state = MachineManager.getMachineState(getMachineUUID());

            if (isUnloaded()) {
                // Resume the unloaded powered machine
                resume();
            }

            if (state != null) {
                if (state.getGPIO() != null) {
                    // Update redstone output
                    outRedstoneSignals(state.getGPIO().read_pins());
                }
                if (state.getDisplay() != null) {
                    PacketDispatcher.sendToAllAround(new DisplayPacket(getMachineUUID()), this, 10);
                }
            }
        }
    }

    // Handle redstone input
    @Override
    public void inRedstoneSignals(int signals) {
        MachineState state = MachineManager.getMachineState(getMachineUUID());
        if (state != null && state.getGPIO() != null) {
            state.getGPIO().write_pins(signals);
        }
    }
}
