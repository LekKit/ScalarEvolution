package lekkit.scev.items.util;

import java.util.List;
import java.util.Vector;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class LoreUtil {
    private class StrPair {
        StrPair(String text, String post) {
            key = text;
            val = post;
        }
        public String key;
        public String val;
    }

    private Vector<StrPair> customLore = new Vector<StrPair>();

    public void addLore(String text, String post) {
        customLore.add(new StrPair(text, post));
    }

    public void provideLore(ItemStack stack, EntityPlayer player, List<String> list) {
        boolean hasLore = false;
        list.add("");

        for (int i=0; i<10; ++i) {
            if (StatCollector.canTranslate(stack.getUnlocalizedName() + ".lore" + i)) {
                list.add(StatCollector.translateToLocal(stack.getUnlocalizedName() + ".lore" + i));
                hasLore = true;
            } else {
                break;
            }
        }

        if (hasLore) {
            list.add("");
        }
    }

    public void provideInfo(ItemStack stack, EntityPlayer player, List<String> list) {
        if (customLore.size() > 0) {
            for (StrPair pair : customLore) {
                list.add(StatCollector.translateToLocal(pair.key) + pair.val);
            }
            list.add("");
        }

        NBTTagList items = getItemsTag(stack);
        if (items != null && items.tagCount() != 0) {
            list.add(StatCollector.translateToLocal("text.scev.installed_components") + ":");

            enumerateItems(items, list);

            list.add("");
        }
    }

    public NBTTagList getItemsTag(ItemStack stack) {
        try {
            return stack.getTagCompound().getTagList("Items", Constants.NBT.TAG_COMPOUND);
        } catch (Throwable e) {}
        return null;
    }

    private String lastItemName = "";
    private int lastItemCount = 0;

    private void pushLastItem(List<String> list) {
        if (lastItemCount == 1) {
            list.add("  §f" + lastItemName);
        } else if (lastItemCount > 1) {
            list.add("  §f" + lastItemName + " x" + lastItemCount);
        }
    }

    public void enumerateItems(NBTTagList items, List<String> list) {
        lastItemName = "";
        lastItemCount = 0;

        if (items != null) try {
            for (int i = 0; i < items.tagCount(); ++i) {
                NBTTagCompound item = (NBTTagCompound)items.getCompoundTagAt(i);
                ItemStack stack = ItemStack.loadItemStackFromNBT(item);
                if (stack != null) {
                    if (lastItemName.equals(stack.getDisplayName())) {
                        lastItemCount++;
                    } else {
                        pushLastItem(list);
                        lastItemName = stack.getDisplayName();
                        lastItemCount = 1;
                    }

                    NBTTagList tags = getItemsTag(stack);
                    if (tags != null && tags.tagCount() != 0) {
                        pushLastItem(list);
                        enumerateItems(tags, list);
                    }
                }
            }
        } catch (Throwable e) {}
    }
}

