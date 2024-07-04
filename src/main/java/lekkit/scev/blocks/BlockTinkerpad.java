package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityTinkerpad;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class BlockTinkerpad extends BlockDirectionalModel {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityTinkerpad();
    }
}
