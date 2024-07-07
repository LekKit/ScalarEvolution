package lekkit.scev.tileentity;

import lekkit.scev.inventory.InventoryMotherboard;
import lekkit.scev.items.ItemMotherboard;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComputerCase extends TileEntityBaseInventory {
    protected static final int computerCaseSlots = 1;
    protected InventoryMotherboard invMotherboard = null;

    public TileEntityComputerCase() {
        super(computerCaseSlots);
    }

    // Upon insertion/removal of the motherboard, open/close it's
    // respective InventoryItem
    protected void updateInvMotherboard() {
        invMotherboard = null;
        ItemStack itemStack = getStackInSlot(0);
        if (itemStack != null && itemStack.getItem() instanceof ItemMotherboard) {
            invMotherboard = new InventoryMotherboard(itemStack);
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot < computerCaseSlots) {
            return stack.getItem() instanceof ItemMotherboard;
        } else if (invMotherboard != null) {
            return invMotherboard.isItemValidForSlot(slot - computerCaseSlots, stack);
        }
        return false;
    }

    @Override
    public int getSizeInventory() {
        return computerCaseSlots + (invMotherboard != null ? invMotherboard.getSizeInventory() : 0);
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < computerCaseSlots) {
            return super.getStackInSlot(slot);
        } else if (invMotherboard != null) {
            return invMotherboard.getStackInSlot(slot - computerCaseSlots);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot < computerCaseSlots) {
            return super.decrStackSize(slot, amount);
        } else if (invMotherboard != null) {
            return invMotherboard.decrStackSize(slot - computerCaseSlots, amount);
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot < computerCaseSlots) {
            super.setInventorySlotContents(slot, stack);
            updateInvMotherboard();
        } else if (invMotherboard != null) {
            invMotherboard.setInventorySlotContents(slot - computerCaseSlots, stack);
        }
    }

    @Override
    public void deserializeFromNBT(NBTTagCompound compound) {
        super.deserializeFromNBT(compound);
        updateInvMotherboard();
    }
}
