package lekkit.scev.items;

import java.util.UUID;

import lekkit.scev.items.util.NBTUtil;

import net.minecraft.item.ItemStack;

public class ItemStorage extends ItemNonStackable {
    public UUID getStorageUUID(ItemStack stack) {
        if (stack.getItem() instanceof ItemStorage) {
            return NBTUtil.getItemUUID(stack, true);
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

