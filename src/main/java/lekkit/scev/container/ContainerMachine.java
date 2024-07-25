package lekkit.scev.container;

import java.util.UUID;

import lekkit.scev.server.IDisplayHandle;
import lekkit.scev.server.IMachineHandle;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerMachine extends ContainerBase {
    private final IMachineHandle machineHandle;
    private final IDisplayHandle displayHandle;

    public ContainerMachine(EntityPlayer player, IMachineHandle inventory, boolean fatGui) {
        super(player, inventory, fatGui);
        machineHandle = inventory;
        displayHandle = null;
    }

    public ContainerMachine(EntityPlayer player, IDisplayHandle display) {
        super(player);
        displayHandle = display;
        if (display instanceof IMachineHandle) {
            machineHandle = (IMachineHandle)display;
        } else {
            machineHandle = null;
        }
    }

    public IDisplayHandle getDisplayHandle() {
        return displayHandle;
    }

    public IMachineHandle getMachineHandle() {
        return machineHandle;
    }

    public UUID getMachineUUID() {
        if (machineHandle != null) {
            return machineHandle.getMachineUUID();
        }
        if (displayHandle != null) {
            return displayHandle.getMachineUUID();
        }
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (!super.canInteractWith(player)) {
            return false;
        }
        if (machineHandle != null) {
            return machineHandle.isValid();
        }
        if (displayHandle != null) {
            return displayHandle.isValid();
        }
        return false;
    }
}
