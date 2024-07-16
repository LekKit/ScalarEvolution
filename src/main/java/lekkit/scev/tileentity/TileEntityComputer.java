package lekkit.scev.tileentity;

import java.util.UUID;

import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComputer extends TileEntityBaseInventory {
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

    public void powerOn() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());

        if (state == null) {
            state = buildMachine(getMachineUUID());
        }

        if (state != null) {
            state.getMachine().start();
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
                    // TODO: Try to resume machine snapshot
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
            MachineManager.destroyMachineState(getMachineUUID());
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();

        if (runningOnServer()) {
            // Pause the running machine
            MachineState state = MachineManager.getMachineState(getMachineUUID());
            if (state != null) {
                state.getMachine().pause();

                unloaded = true;
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (unloaded && runningOnServer()) {
            // Resume the paused powered machine
            MachineState state = MachineManager.getMachineState(getMachineUUID());
            if (state != null && state.getMachine().isPowered()) {
                state.getMachine().start();
            }
        }
    }
}
