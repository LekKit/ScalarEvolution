package lekkit.scev.items;

import net.minecraft.block.Block;

public class ItemBlockNonStackable extends ItemBlockBase {
    public ItemBlockNonStackable(Block block) {
        super(block);
        setMaxStackSize(1);
    }
}
