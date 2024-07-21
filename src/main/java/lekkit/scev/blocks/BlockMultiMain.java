package lekkit.scev.blocks;

import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract class BlockMultiMain extends BlockMachineBase {
    protected BlockMultiPart part = new BlockMultiPart(this);

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if (y + 1 >= world.getHeight()) {
            // Multiblock extends beyond world height
            return false;
        }

        // Check full block
        for (int ty = 0; ty < 2; ++ty) {
            if (!super.canPlaceBlockAt(world, x, y + ty, z)) {
                return false;
            }
        }
        return true;
    }

    public void placeBlockParts(World world, int x, int y, int z) {
        for (int ty = 1; ty < 2; ++ty) {
            world.setBlock(x, y + ty, z, part);
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        placeBlockParts(world, x, y, z);
        return super.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        placeBlockParts(world, x, y, z);
        super.onBlockPlacedBy(world, x, y, z, entity, is);
    }
}
