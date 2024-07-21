package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityWorkstation;
import lekkit.scev.main.ScalarEvolution;

public class BlockWorkstation extends BlockMachineBase {
    public BlockWorkstation() {
        super(TileEntityWorkstation.class, ScalarEvolution.GUI_WORKSTATION_INV);
    }
}
