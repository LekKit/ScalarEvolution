package lekkit.scev.blocks;

import java.util.List;

import lekkit.scev.main.ScalarEvolution;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public abstract class BlockDirectionalModel extends BlockContainer {
    protected int renderID = -1;

    public BlockDirectionalModel() {
        super(Material.iron);
        setHardness(2.5f);
    }

    public void setRenderId(int renderID) {
        this.renderID = renderID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType() {
        return renderID;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        // Calculate 16-direction value of block rotation
        int dir = ((int)Math.round((360 - entity.rotationYaw) / 22.5f)) & 15;

        // Rotate opposite from player
        dir ^= 8;

        world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    }

    // Override to change block bounding box
    public void setBlockBoundsBasedOnDirection(int direction) {}


    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        int metadata = access.getBlockMetadata(x, y, z);
        int direction = ((metadata + 2) / 4) % 4;

        setBlockBoundsBasedOnDirection(direction);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
        setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
    }
}
