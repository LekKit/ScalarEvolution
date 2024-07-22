package lekkit.scev.blocks;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.tileentity.TileEntityPowermark;

public class BlockPowermark extends BlockMachineBase {
    public BlockPowermark() {
        super(TileEntityPowermark.class, ScalarEvolution.GUI_COMPUTER_CASE_INV);
    }
}
