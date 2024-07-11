package lekkit.scev.inventory;

import lekkit.scev.main.ScalarEvolution;

import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/*
 * Vanilla Slot class is damned in some regards,
 * so let's fix some of its shortcomings.
 */

public class SlotBase extends Slot {
    protected final int slotIndex;
    protected final String slotBackground;

    public SlotBase(IInventory inventory, int index, int xPos, int yPos, String bgName) {
        super(inventory, index, xPos, yPos);

        // Because this.slotNumber is counted including player inventory slots...
        slotIndex = index;
        slotBackground = bgName;
    }

    public SlotBase(IInventory inventory, int index, int xPos, int yPos) {
        this(inventory, index, xPos, yPos, null);
    }

    public String getSlotBackgroundName() {
        return slotBackground;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return this.inventory.isItemValidForSlot(this.slotIndex, stack);
    }

    @Override
    public int getSlotStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }
}
