package lekkit.scev.inventory;

import lekkit.scev.items.ItemMotherboard;

import net.minecraft.inventory.Slot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotMotherboard extends Slot {
    public SlotMotherboard(IInventory inv, int index, int xPos, int yPos) {
        super(inv, index, xPos, yPos);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemMotherboard;
    }
}
