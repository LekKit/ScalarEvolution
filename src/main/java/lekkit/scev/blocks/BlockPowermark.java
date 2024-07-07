package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityPowermark;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class BlockPowermark extends BlockMachineBase {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityPowermark();
    }
}
