package lekkit.scev;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class BlockDirectionalModel extends BlockContainer {
    public BlockDirectionalModel() {
        super(Material.iron);
        setHardness(2.5f);
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
        return -1;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
        // Calculate 16-direction value of block rotation
        int dir = ((int)Math.floor(((360F - entity.rotationYaw) / 22.5f) + 0.5f)) & 15;

        world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        String no_type_prefix = getUnlocalizedName().replace("tile.", "").replace("block.", "");
        blockIcon = ir.registerIcon(no_type_prefix.replace(ScalarEvolution.MODID + ".", ScalarEvolution.MODID + ":"));
    }
}
