package lekkit.scev.blocks;

import lekkit.scev.main.ScalarEvolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTileBase extends BlockDirectionalModel {
    protected final Class<? extends TileEntity> teClass;
    protected final int guiId;
    protected final int shiftGuiId;

    public BlockTileBase(Class<? extends TileEntity> teClass, int guiId, int shiftGuiId) {
        this.teClass = teClass;
        this.guiId = guiId;
        this.shiftGuiId = shiftGuiId;
    }

    public BlockTileBase(Class<? extends TileEntity> teClass, int guiId) {
        this(teClass, guiId, -1);
    }

    public BlockTileBase() {
        this(null, -1, -1);
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
        if (this.guiId != -1 && !player.isSneaking()) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null) {
                if (!world.isRemote) {
                    player.openGui(ScalarEvolution.instance, this.guiId, world, x, y, z);
                }
                return true;
            }
        } else if (this.shiftGuiId != -1 && player.isSneaking()) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null) {
                if (!world.isRemote) {
                    player.openGui(ScalarEvolution.instance, this.shiftGuiId, world, x, y, z);
                }
                return true;
            }
        }
        return false;
    }
}
