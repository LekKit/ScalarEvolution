package lekkit.scev.blocks;

import java.util.ArrayList;

import lekkit.scev.main.ScalarEvolution;
import lekkit.scev.tileentity.TileEntityBase;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BlockMachineBase extends BlockDirectionalModel {

    /*
     * Lazy ass code for prototyping
     */

    protected final Class<? extends TileEntity> teClass;
    protected final int guiId;

    public BlockMachineBase(Class<? extends TileEntity> teClass, int guiId) {
        this.teClass = teClass;
        this.guiId = guiId;
    }

    public BlockMachineBase() {
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
            if (te != null && !player.isSneaking() && !world.isRemote) {
                player.openGui(ScalarEvolution.instance, this.guiId, world, x, y, z);
                return true;
            }
        }
        return false;
    }

    /*
     * Save & restore NBT data when breaking/placing machines
     */

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityBase && stack.getTagCompound() != null) {
            TileEntityBase teBase = (TileEntityBase)te;
            teBase.deserializeFromNBT(stack.getTagCompound());
        }

        // Consume machine ItemBlock when placing in creative
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (player.capabilities.isCreativeMode) {
                player.inventory.decrStackSize(player.inventory.currentItem, 1);
            }
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ItemStack stack = new ItemStack(world.getBlock(x, y, z), 1, metadata);
        TileEntity te = world.getTileEntity(x, y, z);
        NBTTagCompound tag = new NBTTagCompound();

        if (te instanceof TileEntityBase && !stack.hasTagCompound()) {
            TileEntityBase teBase = (TileEntityBase)te;
            teBase.serializeToNBT(tag);
            stack.setTagCompound(tag);
        }

        ret.add(stack);
        return ret;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        if (willHarvest) {
            // Delay block deletion (block is destroyed in harvestBlock)
            return true;
        }
        if (player.capabilities.isCreativeMode) {
            // Drop machine when breaking in creative
            harvestBlock(world, player, x, y, z, world.getBlockMetadata(x, y, z));
        }
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    /*
     * Connect to redstone wires
     */

    @Override
    public boolean canProvidePower() {
        return true;
    }

}
