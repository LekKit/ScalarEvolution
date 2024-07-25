package lekkit.scev.items;

import java.util.List;

import lekkit.scev.main.ScalarEvolution;

import lekkit.scev.items.util.LoreUtil;
import lekkit.scev.entity.item.EntityItemBase;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemBase extends Item {
    protected LoreUtil lore = new LoreUtil();
    protected int guiId = -1;
    protected int shiftGuiId = -1;

    public void addLore(String text) {
        lore.addLore(text);
    }

    public void setGuiId(int guiId) {
        this.guiId = guiId;
    }

    public void setShiftGuiId(int shiftGuiId) {
        this.shiftGuiId = shiftGuiId;
    }

    /*
     * Lore handling
     */

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean wtf) {
        lore.provideLore(stack, player, list);
        lore.provideInfo(stack, player, list);
    }

    /*
     * EntityItem handling
     */

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public int getEntityLifespan(ItemStack stack, World world) {
        if (isItemDestructible(stack)) {
            return super.getEntityLifespan(stack, world);
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public Entity createEntity(World world, Entity entityItem, ItemStack stack) {
        return new EntityItemBase(world, entityItem, stack);
    }

    /*
     * Item GUI handling
     */

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (guiId != -1 && !player.isSneaking()) {
            if (!world.isRemote) {
                player.openGui(ScalarEvolution.instance, guiId, world, 0, 0, 0);
            }
        }
        if (shiftGuiId != -1 && player.isSneaking()) {
            if (!world.isRemote) {
                player.openGui(ScalarEvolution.instance, shiftGuiId, world, 0, 0, 0);
            }
        }
        return stack;
    }

    // Without this, inventory won't work (Go figure...)
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }

    /*
     * Overridable APIs
     */

    public boolean isItemDestructible(ItemStack stack) {
        return true;
    }

    public void onItemDestroyed(ItemStack stack) {
        System.out.println("Destroyed item " + stack.getDisplayName());
    }
}

