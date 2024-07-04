package lekkit.scev;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;

public class BlockMachine extends BlockDirectionalModel {
    protected Class<? extends TileEntity> teClass;

    public BlockMachine(Class<? extends TileEntity> teClass) {
        this.teClass = teClass;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        try {
            return teClass.newInstance();
        } catch (Throwable e) {
            // Well fuck
            return null;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float clickX, float clickY, float clickZ) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        //player.openGui(OpenPrinter.instance, 5, world, x, y, z);
        return true;
    }
}
