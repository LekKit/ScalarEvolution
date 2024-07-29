package lekkit.scev.packet.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lekkit.scev.container.ContainerMachine;
import lekkit.scev.packet.AbstractServerMessageHandler;
import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;
import net.minecraft.entity.player.EntityPlayer;

public class MachineInputPacket implements IMessage {
    public static final byte INPUT_KEYBOARD_PRESS = 0;
    public static final byte INPUT_KEYBOARD_RELEASE = 1;
    public static final byte INPUT_MOUSE_MOVE = 2;
    public static final byte INPUT_MOUSE_PLACE = 3;
    public static final byte INPUT_MOUSE_SCROLL = 4;
    public static final byte INPUT_MOUSE_PRESS = 5;
    public static final byte INPUT_MOUSE_RELEASE = 6;

    public byte inputType;
    public byte key;
    public short mouseX;
    public short mouseY;

    public MachineInputPacket() {}

    public MachineInputPacket(byte inputType, byte key) {
        this.inputType = inputType;
        this.key = key;
    }

    public MachineInputPacket(byte inputType, short mouseX, short mouseY) {
        this.inputType = inputType;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.inputType = buffer.readByte();

        switch (inputType) {
            case INPUT_KEYBOARD_PRESS:
            case INPUT_KEYBOARD_RELEASE:
            case INPUT_MOUSE_PRESS:
            case INPUT_MOUSE_RELEASE:
            case INPUT_MOUSE_SCROLL:
                this.key = buffer.readByte();
                break;
            case INPUT_MOUSE_MOVE:
            case INPUT_MOUSE_PLACE:
                this.mouseX = buffer.readShort();
                this.mouseY = buffer.readShort();
                break;
        }
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeByte(this.inputType);
        switch (inputType) {
            case INPUT_KEYBOARD_PRESS:
            case INPUT_KEYBOARD_RELEASE:
            case INPUT_MOUSE_PRESS:
            case INPUT_MOUSE_RELEASE:
            case INPUT_MOUSE_SCROLL:
                buffer.writeByte(this.key);
                break;
            case INPUT_MOUSE_MOVE:
            case INPUT_MOUSE_PLACE:
                buffer.writeShort(this.mouseX);
                buffer.writeShort(this.mouseY);
                break;
        }
    }

    public static class Handler extends AbstractServerMessageHandler<MachineInputPacket> {
        @Override
        public IMessage handleServerMessage(EntityPlayer player, MachineInputPacket message, MessageContext ctx) {
            try {
                if (player.openContainer instanceof ContainerMachine) {
                    ContainerMachine container = (ContainerMachine)player.openContainer;
                    MachineState state = MachineManager.getMachineState(container.getMachineUUID());

                    if (state != null) {
                        // Machine input handling

                        switch (message.inputType) {
                            case INPUT_KEYBOARD_PRESS:
                                state.getKeyboard().press(message.key);
                                break;
                            case INPUT_KEYBOARD_RELEASE:
                                state.getKeyboard().release(message.key);
                                break;
                            case INPUT_MOUSE_PRESS:
                                state.getMouse().press(message.key);
                                break;
                            case INPUT_MOUSE_RELEASE:
                                state.getMouse().release(message.key);
                                break;
                            case INPUT_MOUSE_SCROLL:
                                state.getMouse().scroll(message.key);
                                break;
                            case INPUT_MOUSE_MOVE:
                                state.getMouse().move(message.mouseX, message.mouseY);
                                break;
                            case INPUT_MOUSE_PLACE:
                                state.getMouse().place(message.mouseX, message.mouseY);
                                break;
                        }
                    }
                }
            } catch (Throwable e) {}
            return null;
        }
    }
}
