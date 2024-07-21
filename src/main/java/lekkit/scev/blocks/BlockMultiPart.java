package lekkit.scev.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMultiPart extends Block {
    protected BlockMultiMain mainBlock;

    public BlockMultiPart(BlockMultiMain mainBlock) {
        super(Material.iron);
        setHardness(2.5f);
        this.mainBlock = mainBlock;
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float clickX, float clickY, float clickZ) {
        Block block = world.getBlock(x, y - 1, z);
        if (block == mainBlock) {
            return block.onBlockActivated(world, x, y - 1, z, player, metadata, clickX, clickY, clickZ);
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        mainBlock.registerBlockIcons(ir);
    }
}
