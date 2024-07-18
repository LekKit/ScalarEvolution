package lekkit.scev.tileentity;

import java.util.UUID;

import lekkit.scev.inventory.InventoryMotherboard;
import lekkit.scev.items.ItemMotherboard;
import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;

import lekkit.scev.items.*;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComputerCase extends TileEntityComputer {
    protected static final int computerCaseSize = 1;
    protected InventoryMotherboard invMotherboard = null;

    public TileEntityComputerCase() {
        super(computerCaseSize);
    }

    /*
     * Build machine based on computer components
     */

    @Override
    protected MachineState buildMachine(UUID uuid) {
        if (invMotherboard == null) {
            // No motherboard installed
            return null;
        }

        if (invMotherboard.getStackInSlot(0) == null) {
            // No CPU installed
            return null;
        }

        if (invMotherboard.getStackInSlot(1) == null) {
            // No firmware chip installed
            return null;
        }

        int mem_mb = 0;
        for (int i = 0; i < invMotherboard.getSizeInventory(); ++i) {
            ItemStack stack = invMotherboard.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemRAM) {
                ItemRAM item = (ItemRAM)stack.getItem();
                mem_mb += item.getRamMegs();
            }
        }

        if (mem_mb == 0) {
            // No RAM installed
            return null;
        }

        MachineState state = MachineManager.createMachineState(uuid, mem_mb, 1, true);
        if (state == null) {
            return null;
        }

        boolean fail = false;
        for (int i = 0; i < invMotherboard.getSizeInventory(); ++i) {
            ItemStack stack = invMotherboard.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() instanceof ItemFlash) {
                    ItemFlash item = (ItemFlash)stack.getItem();
                    fail = fail || !state.attachFirmwareFlash(item.getStorageUUID(stack), item.getStorageSize(), item.getStorageOrigin());
                } else if (stack.getItem() instanceof ItemNVMe) {
                    ItemNVMe item = (ItemNVMe)stack.getItem();
                    fail = fail || !state.attachNVMeDrive(item.getStorageUUID(stack), item.getStorageSize(), item.getStorageOrigin());
                }
            }
        }

        if (fail) {
            MachineManager.destroyMachineState(uuid);
            return null;
        }

        return state;
    }

    /*
     * Computer case inventory implementation
     */

    // Upon insertion/removal of the motherboard, open/close it's respective InventoryItem
    protected void updateInvMotherboard() {
        invMotherboard = null;
        ItemStack itemStack = getStackInSlot(0);
        if (itemStack != null && itemStack.getItem() instanceof ItemMotherboard) {
            invMotherboard = new InventoryMotherboard(itemStack);
        }
    }

    public ItemMotherboard getItemMotherboard() {
        if (invMotherboard != null) {
            return (ItemMotherboard)invMotherboard.getInventoryItemStack().getItem();
        }
        return null;
    }

    public int getComputerCaseSize() {
        return computerCaseSize;
    }

    @Override
    public String getInventoryName() {
        return "container.scev.computer_case";
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot < computerCaseSize) {
            return stack.getItem() instanceof ItemMotherboard;
        } else if (invMotherboard != null) {
            return invMotherboard.isItemValidForSlot(slot - computerCaseSize, stack);
        }
        return false;
    }

    @Override
    public int getSizeInventory() {
        return computerCaseSize + (invMotherboard != null ? invMotherboard.getSizeInventory() : 0);
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot < computerCaseSize) {
            return super.getStackInSlot(slot);
        } else if (invMotherboard != null) {
            return invMotherboard.getStackInSlot(slot - computerCaseSize);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot < computerCaseSize) {
            return super.decrStackSize(slot, amount);
        } else if (invMotherboard != null) {
            ItemStack stack = invMotherboard.decrStackSize(slot - computerCaseSize, amount);
            super.markDirty();
            return stack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot < computerCaseSize) {
            super.setInventorySlotContents(slot, stack);
            updateInvMotherboard();
        } else if (invMotherboard != null) {
            invMotherboard.setInventorySlotContents(slot - computerCaseSize, stack);
            super.markDirty();
        }
    }

    @Override
    public void deserializeFromNBT(NBTTagCompound compound) {
        super.deserializeFromNBT(compound);
        updateInvMotherboard();
    }
}
