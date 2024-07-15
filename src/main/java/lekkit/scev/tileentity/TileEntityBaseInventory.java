package lekkit.scev.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class TileEntityBaseInventory extends TileEntityBase implements IInventory {
    private final int inv_size;
    private ItemStack[] inventory;

    public TileEntityBaseInventory(int size) {
        inv_size = size;
        inventory = new ItemStack[inv_size];
    }

    public int getTileInventorySize() {
        return inventory.length;
    }

    @Override
    public int getSizeInventory() {
        return getTileInventorySize();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize > amount) {
                stack = stack.splitStack(amount);
                // Don't forget this line or your inventory will not be saved!
                markDirty();
            } else {
                // this method also calls onInventoryChanged, so we don't need to call it again
                setInventorySlotContents(slot, null);
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        setInventorySlotContents(slot, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }

        // Don't forget this line or your inventory will not be saved!
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        // Is the TileEntity still alive and reachable by the player?
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
            && player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        return true;
    }

    @Override
    public void deserializeFromNBT(NBTTagCompound compound) {
        NBTTagList items = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);

        inventory = new ItemStack[getTileInventorySize()];
        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
            int slot = item.getInteger("Slot");

            // Just double-checking that the saved slot index is within our inventory array bounds
            if (slot >= 0 && slot < getTileInventorySize()) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
    }

    @Override
    public void serializeToNBT(NBTTagCompound compound) {
        // Create a new NBT Tag List to store itemstacks as NBT Tags
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < getTileInventorySize(); ++i) {
            // Only write stacks that contain items
            if (getStackInSlot(i) != null) {
                // Make a new NBT Tag Compound to write the itemstack and slot index to
                NBTTagCompound item = new NBTTagCompound();
                item.setInteger("Slot", i);
                // Writes the itemstack in slot(i) to the Tag Compound we just made
                getStackInSlot(i).writeToNBT(item);

                // add the tag compound to our tag list
                items.appendTag(item);
            }
        }
        // Add the TagList to the ItemStack's Tag Compound with the name "Items"
        compound.setTag("Items", items);
    }
}
