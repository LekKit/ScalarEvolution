package lekkit.scev.tileentity;

import java.util.UUID;

import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComputer extends TileEntityBaseInventory {
    protected UUID machineUUID;

    public TileEntityComputer(int invSize) {
        super(invSize);

        machineUUID = UUID.randomUUID();
    }

    @Override
    public void deserializeFromNBT(NBTTagCompound compound) {
        super.deserializeFromNBT(compound);

        String uuidString = compound.getString("UUID");
        if (uuidString != null) {
            machineUUID = UUID.fromString(uuidString);

            // Try to resume machine snapshot
            MachineManager.tryResumeMachineState(machineUUID);
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

        if (!getWorldObj().isRemote) {
            // Kill the running machine
            MachineManager.destroyMachineState(machineUUID);
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();

        if (!getWorldObj().isRemote) {
            // Pause the running machine
            MachineState state = MachineManager.getMachineState(machineUUID);
            if (state != null) {
                state.getMachine().pause();
            }
        }
    }

    public void updateEntity() {
        super.updateEntity();

        if (!getWorldObj().isRemote) {
            // Resume the paused powered machine
            MachineState state = MachineManager.getMachineState(machineUUID);
            if (state != null && state.getMachine().isPowered()) {
                state.getMachine().start();
            }
        }
    }
}
