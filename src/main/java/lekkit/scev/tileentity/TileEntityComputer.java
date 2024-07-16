package lekkit.scev.tileentity;

import java.util.UUID;

import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComputer extends TileEntityBaseInventory {
    protected UUID machineUUID;
    protected boolean running = false;
    protected boolean paused = false;

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

    public void pause() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());

        if (state != null) {
            state.getMachine().pause();

            paused = true;
        }
    }

    public void resume() {
        if (paused) {
            MachineState state = MachineManager.getMachineState(getMachineUUID());

            if (state != null) {
                state.getMachine().start();
            }
        }
        paused = false;
    }

    public void powerOn() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());

        if (state == null) {
            state = buildMachine(getMachineUUID());
        }

        if (state != null) {
            state.getMachine().start();
            running = true;
        }
    }

    public void powerOff() {
        MachineManager.destroyMachineState(getMachineUUID());
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

    public void power() {
        if (isRunning()) {
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
            powerOff();
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();

        if (runningOnServer()) {
            // Pause the running machine
            pause();
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (isPaused() && runningOnServer()) {
            // Resume the paused powered machine
            resume();
        }
    }
}
