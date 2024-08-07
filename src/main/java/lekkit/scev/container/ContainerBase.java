package lekkit.scev.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBase extends Container {
    private static final int INV_START = 0;
    private static final int INV_SIZE = 36;
    private static final int CONT_START = INV_SIZE;

    private final boolean fatGui;

    private final EntityPlayer player;
    private final IInventory inventory;

    public ContainerBase(EntityPlayer player, IInventory inventory, boolean fatGui) {
        // More stupid magic numbers sorta resembling ContainerChest
        int yOffset = fatGui ? 56 : 0;

        // Player inventory
        // This should be ALWAYS present, otherwise server randomly crashes
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, i * 9 + j + 9, 8 + j * 18, yOffset + 84 + i * 18));
            }
        }

        // Player action bar
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, yOffset + 142));
        }

        this.player = player;
        this.inventory = inventory;
        this.fatGui = fatGui;
    }

    public ContainerBase(EntityPlayer player, IInventory inventory) {
        this(player, inventory, false);
    }

    public ContainerBase(EntityPlayer player) {
        this(player, null, false);
    }

    public int customSlotsSize() {
        return inventorySlots.size() - INV_SIZE;
    }

    public void removeSlotFromContainer(IInventory inventory, int slotIndex) {
        Slot slot = getSlotFromInventory(inventory, slotIndex);
        if (slot != null) {
            inventorySlots.remove(slot);
        }
    }

    public IInventory getPlayerInventory() {
        return player.inventory;
    }

    public IInventory getContainerInventory() {
        return inventory;
    }

    public boolean isFatGui() {
        return fatGui;
    }

    // Handle shift-click
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack slot_stack = slot.getStack();
            itemstack = slot_stack.copy();

            if (index < INV_SIZE) {
                // Transfer from player inventory
                if (!this.mergeItemStack(slot_stack, CONT_START, CONT_START + customSlotsSize(), false)) {
                    return null;
                }
            } else {
                // Transfer to player inventory
                if (!this.mergeItemStack(slot_stack, INV_START, INV_SIZE, true)) {
                    return null;
                }
                slot.onSlotChange(slot_stack, itemstack);
            }

            if (slot_stack.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }

            if (slot_stack.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, slot_stack);
        }

        return itemstack;
    }

    // Fix for broken vanilla implementation
    @Override
    protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean backwards) {
        boolean ret = false;
        int off = (backwards ? -1 : 1);

        // Try to merge with existing non-full item stacks
        if (stack.isStackable()) {
            int pos = (backwards ? end - 1 : start);
            while (stack.stackSize > 0 && (!backwards && pos < end || backwards && pos >= start)) {
                Slot slot = (Slot)inventorySlots.get(pos);
                ItemStack slot_stack = slot.getStack();

                if (slot.isItemValid(stack) && slot_stack != null
                 && slot_stack.getItem() == stack.getItem()
                 && (!stack.getHasSubtypes() || stack.getItemDamage() == slot_stack.getItemDamage())
                 && ItemStack.areItemStackTagsEqual(stack, slot_stack)) {
                    int sum = slot_stack.stackSize + stack.stackSize;

                    if (sum <= stack.getMaxStackSize() && sum <= slot.getSlotStackLimit()) {
                        stack.stackSize = 0;
                        slot_stack.stackSize = sum;
                        slot.inventory.markDirty();
                        ret = true;
                    } else if (slot_stack.stackSize < stack.getMaxStackSize() && sum < slot.getSlotStackLimit()) {
                        stack.stackSize -= stack.getMaxStackSize() - slot_stack.stackSize;
                        slot_stack.stackSize = stack.getMaxStackSize();
                        slot.inventory.markDirty();
                        ret = true;
                    }
                }

                pos += off;
            }
        }

        // Merge remaining items into empty slots
        if (stack.stackSize > 0) {
            int pos = (backwards ? end - 1 : start);
            while (!backwards && pos < end || backwards && pos >= start) {
                Slot slot = (Slot)inventorySlots.get(pos);
                ItemStack slot_stack = slot.getStack();

                if (slot.isItemValid(stack)) {
                    if (slot_stack == null) {
                        int l = stack.stackSize;
                        if (l <= slot.getSlotStackLimit()) {
                            slot.putStack(stack.copy());
                            stack.stackSize = 0;
                            slot.inventory.markDirty();
                            ret = true;
                            break;
                        } else {
                            putStackInSlot(pos, new ItemStack(stack.getItem(), slot.getSlotStackLimit(), stack.getItemDamage()));
                            stack.stackSize -= slot.getSlotStackLimit();
                            slot.inventory.markDirty();
                            ret = true;
                        }
                    }
                }

                pos += off;
            }
        }

        return ret;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (inventory != null) {
            return inventory.isUseableByPlayer(player);
        }
        return true;
    }
}
