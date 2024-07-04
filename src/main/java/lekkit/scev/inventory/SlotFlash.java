package lekkit.scev.inventory;

import lekkit.scev.items.ItemFlash;

import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class SlotFlash extends Slot {
    public SlotFlash(IInventory inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemFlash;
    }
}
