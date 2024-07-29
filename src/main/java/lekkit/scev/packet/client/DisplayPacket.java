package lekkit.scev.packet.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lekkit.scev.client.DisplayManager;
import lekkit.scev.client.DisplayState;
import lekkit.scev.packet.AbstractClientMessageHandler;
import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;

public class DisplayPacket implements IMessage {
    private UUID uuid = null;

    public DisplayPacket() {}

    public DisplayPacket(UUID machineUUID) {
        this.uuid = machineUUID;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        long leastBits = buffer.readLong();
        long mostBits = buffer.readLong();
        uuid = new UUID(mostBits, leastBits);

        short width = buffer.readShort();
        short height = buffer.readShort();

        if (width > 0 && width < 1024 && height > 0 && height < 768) {
            DisplayState display = DisplayManager.createDisplayState(uuid, width, height);

            if (display != null) {
                display.getBuffer().rewind();
                buffer.readBytes(display.getBuffer());
                display.markDirty();
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        MachineState state = MachineManager.getMachineState(uuid);
        if (state != null && state.getDisplay() != null) {
            buffer.writeLong(uuid.getLeastSignificantBits());
            buffer.writeLong(uuid.getMostSignificantBits());

            buffer.writeShort((short)state.getDisplay().getWidth());
            buffer.writeShort((short)state.getDisplay().getHeight());

            state.getDisplay().getBuffer().rewind();
            buffer.writeBytes(state.getDisplay().getBuffer());
        }
    }

    public static class Handler extends AbstractClientMessageHandler<DisplayPacket> {
        @Override
        public IMessage handleClientMessage(DisplayPacket message, MessageContext ctx) {
            try {

            } catch (Throwable e) {}
            return null;
        }
    }
}
