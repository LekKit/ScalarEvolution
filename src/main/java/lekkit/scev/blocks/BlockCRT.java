package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityCRT;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class BlockCRT extends BlockDirectionalModel {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityCRT();
    }
}
