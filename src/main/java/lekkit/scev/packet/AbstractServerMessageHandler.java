package lekkit.scev.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public abstract class AbstractServerMessageHandler<T extends IMessage> extends AbstractMessageHandler<T> {

    public final IMessage handleClientMessage(T message, MessageContext ctx) {
        return null;
    }
}

