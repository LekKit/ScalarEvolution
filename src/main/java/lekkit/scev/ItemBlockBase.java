package lekkit.scev;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class ItemBlockBase extends ItemBlock {
    public ItemBlockBase(Block block) {
        super(block);
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
    }
}

