package lekkit.scev.items;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStorage extends ItemNonStackable {
    public UUID getStorageUUID(ItemStack stack) {
        if (stack.getItem() instanceof ItemStorage) {
            UUID uuid = null;
            try {
                NBTTagCompound compound = stack.getTagCompound();
                if (compound != null) {
                    uuid = UUID.fromString(compound.getString("UUID"));
                }
            } catch (Throwable e) {}

            if (uuid == null) {
                NBTTagCompound compound = stack.getTagCompound();
                if (compound == null) {
                    compound = new NBTTagCompound();
                    stack.setTagCompound(compound);
                }
                uuid = UUID.randomUUID();
                compound.setString("UUID", uuid.toString());
            }

            return uuid;
        }

        System.out.println("Not a storage item!");
        return null;
    }

    // Original image to instantiate from
    public String getStorageOrigin() {
        return null;
    }

    // In megabytes
    public long getStorageSize() {
        return 0;
    }
}

