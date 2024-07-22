package lekkit.scev.blocks;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.tileentity.TileEntityTinkerpad;

public class BlockTinkerpad extends BlockMachineBase {
    public BlockTinkerpad() {
        super(TileEntityTinkerpad.class, ScalarEvolution.GUI_COMPUTER_CASE_INV);
    }
}
