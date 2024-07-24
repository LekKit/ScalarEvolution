package lekkit.scev.items;

import java.util.List;

import lekkit.scev.items.util.LoreUtil;
import lekkit.scev.entity.item.EntityItemBase;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ItemBlockBase extends ItemBlock {
    LoreUtil lore = new LoreUtil();

    public ItemBlockBase(Block block) {
        super(block);
    }

    public void addLore(String text) {
        lore.addLore(text);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean wtf) {
        lore.provideLore(stack, player, list);
        lore.provideInfo(stack, player, list);
    }

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

    public boolean isItemDestructible(ItemStack stack) {
        return true;
    }

    public void onItemDestroyed(ItemStack stack) {
        System.out.println("Destroyed item " + stack.getDisplayName());
    }
}

