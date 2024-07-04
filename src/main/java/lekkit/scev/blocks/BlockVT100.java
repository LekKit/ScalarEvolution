package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityVT100;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class BlockVT100 extends BlockDirectionalModel {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityVT100();
    }
}
