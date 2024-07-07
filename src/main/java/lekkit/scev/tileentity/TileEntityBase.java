package lekkit.scev.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.NetworkManager;

public class TileEntityBase extends TileEntity {

    /*
     * Custom serialization/deserialization without syncing vanilla NBT
     */

    public void deserializeFromNBT(NBTTagCompound compound) {}
    public void serializeToNBT(NBTTagCompound compound) {}

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.deserializeFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
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
}

