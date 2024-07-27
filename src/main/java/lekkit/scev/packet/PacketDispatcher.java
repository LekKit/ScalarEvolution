package lekkit.scev.packet;

import lekkit.scev.main.ScalarEvolution;

import lekkit.scev.packet.server.*;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraft.tileentity.TileEntity;

public class PacketDispatcher {
    private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(ScalarEvolution.MODID);
    private static byte packetId = 0;

    /**
    * Call this during pre-init or loading and register all of your packets (messages) here
    */
    public static final void registerPackets() {
        registerMessage(MachineResetPacket.Handler.class, MachineResetPacket.class, Side.SERVER);
        registerMessage(MachineInputPacket.Handler.class, MachineInputPacket.class, Side.SERVER);
    }

    /**
    * Registers a message and message handler
    */
    private static final <REQ extends IMessage,REPLY extends IMessage>
    void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass, Side side) {
        dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
    }

    private static final <REQ extends IMessage,REPLY extends IMessage>
    void registerBiMessage(Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass) {
        dispatcher.registerMessage(handlerClass, messageClass, packetId, Side.CLIENT);
        dispatcher.registerMessage(handlerClass, messageClass, packetId++, Side.SERVER);
    }

    private static final <REQ extends IMessage,REPLY extends IMessage>
    void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass) {
        Side side = AbstractClientMessageHandler.class.isAssignableFrom(handlerClass) ? Side.CLIENT : Side.SERVER;
        registerMessage(handlerClass, messageClass, side);
    }

    /**
    * Send this message to the specified player.
    * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
    */
    public static final void sendTo(IMessage message, EntityPlayerMP player) {
        dispatcher.sendTo(message, player);
    }

    /**
    * Send this message to everyone within a certain range of a point.
    * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, NetworkRegistry.TargetPoint)}
    */
    public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        dispatcher.sendToAllAround(message, point);
    }

    /**
    * Sends a message to everyone within a certain range of the coordinates in the same dimension.
    */
    public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
        sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

    /**
    * Sends a message to everyone within a certain range of the player provided.
    */
    public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
        sendToAllAround(message, player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, range);
    }

    /**
    * Sends a message to everyone within a certain range of the tile entity provided.
    */
    public static final void sendToAllAround(IMessage message, TileEntity tileEntity, double range) {
        sendToAllAround(message, tileEntity.getWorldObj().provider.dimensionId, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, range);
    }

    /**
    * Send this message to everyone within the supplied dimension.
    * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
    */
    public static final void sendToDimension(IMessage message, int dimensionId) {
        dispatcher.sendToDimension(message, dimensionId);
    }

    /**
    * Send this message to the server.
    * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
    */
    public static final void sendToServer(IMessage message) {
        dispatcher.sendToServer(message);
    }
}
