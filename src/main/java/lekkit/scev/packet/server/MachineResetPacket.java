package lekkit.scev.packet.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lekkit.scev.container.ContainerMachine;
import lekkit.scev.packet.AbstractServerMessageHandler;
import lekkit.scev.server.IMachineHandle;
import net.minecraft.entity.player.EntityPlayer;

public class MachineResetPacket implements IMessage {
    public boolean reset;

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
            try {
                if (player.openContainer instanceof ContainerMachine) {
                    ContainerMachine container = (ContainerMachine)player.openContainer;

                    if (container.getMachineHandle() instanceof IMachineHandle) {
                        // Machine power/reset handling

                        if (message.reset) {
                            container.getMachineHandle().reset();
                        } else {
                            container.getMachineHandle().power();
                        }
                    }
                }
            } catch (Throwable e) {}
            return null;
        }
    }
}
