package lekkit.scev.items.util;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {
    public static UUID getCompoundUUID(NBTTagCompound compound) {
        try {
            return UUID.fromString(compound.getString("UUID"));
        } catch (Throwable e) {}
        return null;
    }

    public static UUID getItemUUID(ItemStack stack, boolean gen) {
        UUID uuid = getCompoundUUID(stack.getTagCompound());

        // Only for server side
        if (uuid == null && gen) {
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

    public static UUID getItemUUID(ItemStack stack) {
        return getItemUUID(stack, false);
    }
}
