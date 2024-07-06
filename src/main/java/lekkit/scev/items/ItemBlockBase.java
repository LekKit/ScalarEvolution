package lekkit.scev.items;

import java.util.List;
import java.util.Vector;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class ItemBlockBase extends ItemBlock {
    private class StrPair {
        StrPair(String text, String post) {
            key = text;
            val = post;
        }
        public String key;
        public String val;
    }

    private Vector<StrPair> customLore = new Vector<StrPair>();

    public ItemBlockBase(Block block) {
        super(block);
    }

    public void addLore(String text, String post) {
        customLore.add(new StrPair(text, post));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean wtf) {
        list.add("");

        for (int i=0; i<10; ++i) {
            if (StatCollector.canTranslate(getUnlocalizedName() + ".lore" + i)) {
                list.add(StatCollector.translateToLocal(getUnlocalizedName() + ".lore" + i));
            } else {
                break;
            }
        }

        for (StrPair pair : customLore) {
            list.add(StatCollector.translateToLocal(pair.key) + pair.val);
        }
    }
}

