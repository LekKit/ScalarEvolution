package lekkit.scev.blocks;

import java.util.ArrayList;

import lekkit.scev.tileentity.TileEntityBase;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BlockMachineBase extends BlockTileBase {
    public BlockMachineBase(Class<? extends TileEntity> teClass, int guiId, int shiftGuiId) {
        super(teClass, guiId, shiftGuiId);
    }

    public BlockMachineBase(Class<? extends TileEntity> teClass, int guiId) {
        super(teClass, guiId);
    }

    public BlockMachineBase() {
        super();
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
     * Redstone interaction
     */

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        checkRedstoneSides(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        checkRedstoneSides(world, x, y, z);
    }

    /*
     * Side:
     *   0: DOWN
     *   1: UP
     *   2: NORTH
     *   3: EAST
     *   4: SOUTH
     *   5: WEST
     */

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity te = world.getTileEntity(x, y, z);

        if (te instanceof TileEntityBase) {
            TileEntityBase teBase = (TileEntityBase)te;
            if (((teBase.getOutRedstoneSignals() >> side) & 1) != 0) {
                return 15;
            }
        }
        return 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return isProvidingStrongPower(world, x, y, z, side);
    }

    protected boolean getPowerFrom(World world, int x, int y, int z, int side) {
        return world.getIndirectPowerOutput(x, y, z, side)
            || world.isBlockIndirectlyGettingPowered(x, y, z);
    }

    public void checkRedstoneSides(World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);

        if (te instanceof TileEntityBase) {
            TileEntityBase teBase = (TileEntityBase)te;
            int signals = 0;
            if (getPowerFrom(world, x, y - 1, z, 0)) signals |= 1;
            if (getPowerFrom(world, x, y + 1, z, 1)) signals |= 2;
            if (getPowerFrom(world, x, y, z - 1, 2)) signals |= 4;
            if (getPowerFrom(world, x, y, z + 1, 3)) signals |= 8;
            if (getPowerFrom(world, x - 1, y, z, 4)) signals |= 16;
            if (getPowerFrom(world, x + 1, y, z, 5)) signals |= 32;

            teBase.inRedstoneSignals(signals);
        }
    }
}
