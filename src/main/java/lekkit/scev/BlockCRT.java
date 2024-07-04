package lekkit.scev;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class BlockCRT extends BlockDirectionalModel {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityCRT();
    }

    /*@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float clickX, float clickY, float clickZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        //player.openGui(ScalarEvolution.instance, 5, world, x, y, z);
        return true;
    }*/
}
