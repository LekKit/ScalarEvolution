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

    public boolean isServer() {
        // For whatever reason worldObj is null on world load
        return getWorldObj() == null || !getWorldObj().isRemote;
    }

    @Override
    public void deserializeFromNBT(NBTTagCompound compound) {
        super.deserializeFromNBT(compound);

        String uuidString = compound.getString("UUID");
        if (uuidString != null) {
            machineUUID = UUID.fromString(uuidString);

            if (isServer()) {
                // TODO: Try to resume machine snapshot
            }
        }
    }

    @Override
    public void serializeToNBT(NBTTagCompound compound) {
        super.serializeToNBT(compound);
        compound.setString("UUID", machineUUID.toString());
    }

    @Override
    public void invalidate() {
        super.invalidate();

        if (isServer()) {
            // Kill the running machine
            MachineManager.destroyMachineState(machineUUID);
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();

        if (isServer()) {
            // Pause the running machine
            MachineState state = MachineManager.getMachineState(machineUUID);
            if (state != null) {
                state.getMachine().pause();

                unloaded = true;
            }
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (unloaded && isServer()) {
            // Resume the paused powered machine
            MachineState state = MachineManager.getMachineState(machineUUID);
            if (state != null && state.getMachine().isPowered()) {
                state.getMachine().start();
            }
        }
    }
}
