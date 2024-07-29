package lekkit.scev.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractMessageHandler<T extends IMessage> implements IMessageHandler <T, IMessage> {
    /**
    * Handle a message received on the client side
    * @return a message to send back to the Server, or null if no reply is necessary
    */
    @SideOnly(Side.CLIENT)
    public abstract IMessage handleClientMessage(T message, MessageContext ctx);

    /**
    * Handle a message received on the server side
    * @return a message to send back to the Client, or null if no reply is necessary
    */
    public abstract IMessage handleServerMessage(EntityPlayer player, T message, MessageContext ctx);

    /*
    * Here is where I parse the side and get the player to pass on to the abstract methods.
    * This way it is immediately clear which side received the packet without having to
    * remember or check on which side it was registered and the player is immediately
    * available without a lengthy syntax.
    */
    @Override
    public IMessage onMessage(T message, MessageContext ctx) {
        // due to compile-time issues, FML will crash if you try to use Minecraft.getMinecraft() here,
        // even when you restrict this code to the client side and before the code is ever accessed;
        // a solution is to use your proxy classes to get the player (see below).
        if (ctx.side.isClient()) {
            // the only reason to check side here is to use our more aptly named handling methods
            // client side proxy will return the client side EntityPlayer
            return handleClientMessage(message, ctx);
        } else {
            // server side proxy will return the server side EntityPlayer
            return handleServerMessage(ctx.getServerHandler().playerEntity, message, ctx);
        }
    }
}
