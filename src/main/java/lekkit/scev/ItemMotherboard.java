package lekkit.scev;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemMotherboard extends ItemBase {
    public ItemMotherboard() {
        setMaxStackSize(1);
    }

    // Without this, inventory won't work (Go figure...)
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (!world.isRemote && !player.isSneaking()) {
            player.openGui(ScalarEvolution.instance, ScalarEvolution.GUI_MOTHERBOARD_INV, world, 0, 0, 0);
        }

        return itemstack;
    }
}

