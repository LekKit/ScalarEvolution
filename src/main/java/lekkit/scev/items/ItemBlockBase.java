package lekkit.scev.items;

import java.util.List;

import lekkit.scev.items.util.LoreUtil;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;

public class ItemBlockBase extends ItemBlock {
    LoreUtil lore = new LoreUtil();

    public ItemBlockBase(Block block) {
        super(block);
    }

    public void addLore(String text, String post) {
        lore.addLore(text, post);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean wtf) {
        lore.provideLore(stack, player, list);
        lore.provideInfo(stack, player, list);
    }
}

