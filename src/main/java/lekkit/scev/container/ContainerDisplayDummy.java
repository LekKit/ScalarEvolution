package lekkit.scev.container;

import java.util.UUID;

import lekkit.scev.server.IDisplayHandle;

import net.minecraft.entity.player.EntityPlayer;

public class ContainerDisplayDummy extends ContainerBase {
    protected IDisplayHandle handle;

    public ContainerDisplayDummy(EntityPlayer player, IDisplayHandle handle) {
        super(player);
        this.handle = handle;
    }

    public UUID getMachineUUID() {
        return handle.getMachineUUID();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (handle != null) {
            return handle.isValid();
        }
        return false;
    }
}
