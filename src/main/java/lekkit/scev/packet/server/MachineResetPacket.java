package lekkit.scev.packet.server;

import lekkit.scev.packet.AbstractServerMessageHandler;
import lekkit.scev.container.ContainerBase;
import lekkit.scev.tileentity.TileEntityComputer;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class MachineResetPacket implements IMessage {
    private boolean reset;

    public MachineResetPacket() {}
    public MachineResetPacket(boolean reset) {
        this.reset = reset;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.reset = buffer.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeBoolean(this.reset);
    }

    public static class Handler extends AbstractServerMessageHandler<MachineResetPacket> {
        @Override
        public IMessage handleServerMessage(EntityPlayer player, MachineResetPacket message, MessageContext ctx) {
            if (player.openContainer instanceof ContainerBase) {
                ContainerBase container = (ContainerBase)player.openContainer;
                if (container.getContainerInventory() instanceof TileEntityComputer) {
                    TileEntityComputer te = (TileEntityComputer)container.getContainerInventory();

                    // Machine power/reset handling
                    System.out.println("MachineResetPacket!");
                }
            }
            return null;
        }
    }
}
