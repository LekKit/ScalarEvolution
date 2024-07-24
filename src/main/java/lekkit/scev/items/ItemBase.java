package lekkit.scev.items;

import java.util.List;

import lekkit.scev.items.util.LoreUtil;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;

public class ItemBase extends Item {
    LoreUtil lore = new LoreUtil();

    public void addLore(String text) {
        lore.addLore(text);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean wtf) {
        lore.provideLore(stack, player, list);
        lore.provideInfo(stack, player, list);
    }
}

