package lekkit.scev.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSolderingIron extends ItemNonStackable {
    public ItemSolderingIron() {
        setMaxDamage(25);
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    public boolean hasContainerItem(ItemStack itemStack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        if (itemStack.getItemDamage() < itemStack.getMaxDamage()) {
            ItemStack stack = itemStack.copy();

            stack.setItemDamage(stack.getItemDamage() + 1);
            stack.stackSize = 1;

            return stack;
        }
        return itemStack;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (stack.getItemDamage() < stack.getMaxDamage()) {
            target.setFire(4);
            stack.damageItem(1, attacker);
            return true;
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            //net.minecraft.client.Minecraft.getMinecraft().displayGuiScreen(new MachineGui());
        }
        return stack;
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack)
    {
        return true;
    }
}
