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
    protected final int maxMotherboardLevel;
    protected final int computerCaseSize;
    protected InventoryMotherboard invMotherboard = null;

    public TileEntityComputerCase(int maxMotherboardLevel, int extensionSlots) {
        super(extensionSlots + 1);
        this.maxMotherboardLevel = maxMotherboardLevel;
        this.computerCaseSize = extensionSlots + 1;
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

        for (int i = 0; i < invMotherboard.getSizeInventory(); ++i) {
            ItemStack stack = invMotherboard.getStackInSlot(i);
            if (stack != null) {
                if (!installComponent(stack)) {
                    MachineManager.destroyMachineState(uuid);
                    return null;
                }
            }
        }

        return state;
    }

    public boolean installComponent(ItemStack stack) {
        MachineState state = MachineManager.getMachineState(getMachineUUID());
        if (state != null) {
            if (stack.getItem() instanceof ItemFlash) {
                ItemFlash item = (ItemFlash)stack.getItem();
                return state.attachFirmwareFlash(item.getStorageUUID(stack), item.getStorageSize(), item.getStorageOrigin());
            } else if (stack.getItem() instanceof ItemNVMe) {
                ItemNVMe item = (ItemNVMe)stack.getItem();
                return state.attachNVMeDrive(item.getStorageUUID(stack), item.getStorageSize(), item.getStorageOrigin());
            } else if (stack.getItem() instanceof ItemRTL8169) {
                return state.attachNetworkingCard();
            } else if (stack.getItem() instanceof ItemVideoAdapter) {
                return state.attachVideoAdapter();
            } else if (stack.getItem() instanceof ItemGPIO) {
                return state.attachGPIO();
            }
            return true;
        }
        return false;
    }

    public void removeComponent(ItemStack stack) {
        MachineState state = MachineManager.getMachineState(getMachineUUID());
        if (state != null) {
            if (stack.getItem() instanceof ItemFlash) {
                state.pullFirmwareFlash();
            } else if (stack.getItem() instanceof ItemNVMe) {
                ItemNVMe item = (ItemNVMe)stack.getItem();
                state.pullNVMe(item.getStorageUUID(stack));
            } else if (stack.getItem() instanceof ItemRTL8169) {
                state.pullNetworkingCard();
            } else if (stack.getItem() instanceof ItemVideoAdapter) {
                state.pullVideoAdapter();
            } else if (stack.getItem() instanceof ItemGPIO) {
                state.pullGPIO();
            } else {
                powerOff();
            }
        }
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

    public int getMaxMotherboardLevel() {
        return maxMotherboardLevel;
    }

    @Override
    public String getInventoryName() {
        return "container.scev.computer_case";
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot < computerCaseSize) {
            if (slot == 0) {
                if (stack.getItem() instanceof ItemMotherboard) {
                    ItemMotherboard item = (ItemMotherboard)stack.getItem();
                    return item.getMotherboardLevel() <= getMaxMotherboardLevel();
                }
            }
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
        ItemStack tmpStack = getStackInSlot(slot);
        if (tmpStack != null && runningOnServer()) {
            removeComponent(tmpStack);
        }

        if (slot < computerCaseSize) {
            super.setInventorySlotContents(slot, stack);
            updateInvMotherboard();
        } else if (invMotherboard != null) {
            invMotherboard.setInventorySlotContents(slot - computerCaseSize, stack);
            super.markDirty();
        }

        if (stack != null && runningOnServer()) {
            installComponent(stack);
        }
    }

    @Override
    public void deserializeFromNBT(NBTTagCompound compound) {
        super.deserializeFromNBT(compound);
        updateInvMotherboard();
    }
}
