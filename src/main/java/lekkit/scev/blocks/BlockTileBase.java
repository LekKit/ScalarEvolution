package lekkit.scev.blocks;

import lekkit.scev.main.ScalarEvolution;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;

public class BlockTileBase extends BlockDirectionalModel {
    protected final Class<? extends TileEntity> teClass;
    protected final int guiId;

    public BlockTileBase(Class<? extends TileEntity> teClass, int guiId) {
        this.teClass = teClass;
        this.guiId = guiId;
    }

    public BlockTileBase() {
        this.teClass = null;
        this.guiId = -1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        if (this.teClass != null) {
            try {
                return this.teClass.newInstance();
            } catch (Throwable e) {
                // Well fuck
            }
        }
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float clickX, float clickY, float clickZ) {
        if (this.guiId != -1) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null && !player.isSneaking()) {
                if (!world.isRemote) {
                    player.openGui(ScalarEvolution.instance, this.guiId, world, x, y, z);
                }
                return true;
            }
        }
        return false;
    }
}
