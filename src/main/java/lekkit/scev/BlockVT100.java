package lekkit.scev;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class BlockVT100 extends BlockDirectionalModel {
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityVT100();
    }

    /*@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float clickX, float clickY, float clickZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        // code to open gui explained later
        //player.openGui(OpenPrinter.instance, 5, world, x, y, z);
        return true;
    }*/
}
