package lekkit.scev.tileentity;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComputer extends TileEntityBaseInventory {
    protected UUID machineUUID;

    public TileEntityComputer(int invSize) {
        super(invSize);

        machineUUID = UUID.randomUUID();
    }

    @Override
    public void deserializeFromNBT(NBTTagCompound compound) {
        super.deserializeFromNBT(compound);

        String uuidString = compound.getString("UUID");
        if (uuidString != null) {
            machineUUID = UUID.fromString(uuidString);
        }
        System.out.println("Machine UUID: " + uuidString);
    }

    @Override
    public void serializeToNBT(NBTTagCompound compound) {
        super.serializeToNBT(compound);
        compound.setString("UUID", machineUUID.toString());
    }
}
