package lekkit.scev.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.NetworkManager;

public class TileEntityBase extends TileEntity {
    int redstoneSignals = 0;

    /*
     * Custom serialization/deserialization without syncing vanilla NBT
     */

    public void deserializeFromNBT(NBTTagCompound compound) {}
    public void serializeToNBT(NBTTagCompound compound) {}

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        try {
            if (compound != null) {
                redstoneSignals = compound.getInteger("redstoneSignals");
            }
        } catch (Throwable e) {}

        this.deserializeFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (redstoneSignals != 0) {
            compound.setInteger("redstoneSignals", redstoneSignals);
        }
        this.serializeToNBT(compound);
    }

    /*
     * Sync custom NBT data over network
     */

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        if (compound != null) {
            this.serializeToNBT(compound);
        }
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, compound);
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
       NBTTagCompound compound = packet.func_148857_g();
       if (compound != null) {
          this.deserializeFromNBT(compound);
       }
    }

    /*
     * Redstone interaction
     */

    public void inRedstoneSignals(int signals) {}

    public void outRedstoneSignals(int signals) {
        if (redstoneSignals != signals) {
            redstoneSignals = signals;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public int getOutRedstoneSignals() {
        return redstoneSignals;
    }

    /*
     * Util
     */

    public boolean isValid() {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
    }
}

