package lekkit.scev.inventory;

import java.util.UUID;

import lekkit.scev.items.util.NBTUtil;
import lekkit.scev.items.ItemMotherboard;
import lekkit.scev.items.ItemTinkerpad;
import lekkit.scev.server.IMachineHandle;
import lekkit.scev.server.MachineManager;
import lekkit.scev.server.MachineState;
import lekkit.scev.server.util.ServerUtil;

import lekkit.scev.items.*;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryLaptop extends InventoryItem implements IMachineHandle {
    protected final int maxMotherboardLevel;
    protected final int computerCaseSize;
    protected UUID machineUUID;
    protected InventoryMotherboard invMotherboard = null;

    public InventoryLaptop(ItemStack stack, int maxMotherboardLevel, int extensionSlots) {
        super(stack, extensionSlots + 1);
        this.maxMotherboardLevel = maxMotherboardLevel;
        this.computerCaseSize = extensionSlots + 1;
        this.machineUUID = NBTUtil.getItemUUID(stack, true);
    }

    /*
     * Build machine based on computer components
     */

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
     * Machine implementation
     */

    public UUID getMachineUUID() {
        return machineUUID;
    }

    public boolean isValid() {
        return true;
    }

    protected MachineState initMachineState() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());

        if (state == null) {
            state = buildMachine(getMachineUUID());
        }

        return state;
    }

    public void powerOn() {
        MachineState state = initMachineState();

        if (state != null) {
            state.getMachine().start();
        }
    }

    public void powerOff() {
        MachineManager.destroyMachineState(getMachineUUID());
    }

    public void power() {
        if (isPowered()) {
            powerOff();
        } else {
            powerOn();
        }
    }

    public void reset() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());
        if (state != null) {
            state.getMachine().reset();
        }
    }

    public boolean isPowered() {
        MachineState state = MachineManager.getMachineState(getMachineUUID());
        if (state != null) {
            return state.getMachine().isPowered();
        }
        return false;
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
        return "container.scev.laptop";
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
        if (tmpStack != null && ServerUtil.runningOnServer()) {
            removeComponent(tmpStack);
        }

        if (slot < computerCaseSize) {
            super.setInventorySlotContents(slot, stack);
            updateInvMotherboard();
        } else if (invMotherboard != null) {
            invMotherboard.setInventorySlotContents(slot - computerCaseSize, stack);
            super.markDirty();
        }

        if (stack != null && ServerUtil.runningOnServer()) {
            installComponent(stack);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        updateInvMotherboard();
    }
}
