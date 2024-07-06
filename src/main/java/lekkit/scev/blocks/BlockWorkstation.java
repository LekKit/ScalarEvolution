package lekkit.scev.blocks;

import lekkit.scev.tileentity.TileEntityWorkstation;
import lekkit.scev.main.ScalarEvolution;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class BlockWorkstation extends BlockDirectionalModel {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityWorkstation();
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float clickX, float clickY, float clickZ) {
        if (!player.isSneaking()) {
            if (!world.isRemote) {
                player.openGui(ScalarEvolution.instance, ScalarEvolution.GUI_WORKSTATION_INV, world, x, y, z);
            }
            return true;
        }
        return false;
    }
}
