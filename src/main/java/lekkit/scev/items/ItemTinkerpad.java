package lekkit.scev.items;

import lekkit.scev.main.ScalarEvolution;
import net.minecraft.block.Block;

public class ItemTinkerpad extends ItemBlockNonStackable {
    public ItemTinkerpad(Block block) {
        super(block);
        setGuiId(ScalarEvolution.GUI_DISPLAY_ITEM);
        setShiftGuiId(ScalarEvolution.GUI_LAPTOP_INV);
    }
}
