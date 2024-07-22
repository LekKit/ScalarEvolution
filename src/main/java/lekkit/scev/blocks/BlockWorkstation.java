package lekkit.scev.blocks;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.tileentity.TileEntityWorkstation;

public class BlockWorkstation extends BlockMachineBase {
    public BlockWorkstation() {
        super(TileEntityWorkstation.class, ScalarEvolution.GUI_COMPUTER_CASE_INV);
    }
}
