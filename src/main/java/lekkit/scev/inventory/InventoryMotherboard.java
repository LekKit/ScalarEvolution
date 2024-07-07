package lekkit.scev.inventory;

import lekkit.scev.items.*;

import net.minecraft.item.ItemStack;

public class InventoryMotherboard extends InventoryItem {
    public InventoryMotherboard(ItemStack stack) {
        super(stack, 12);
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
        switch (slotIndex) {
            case 0: return itemstack.getItem() instanceof ItemCPU;
            case 1: return itemstack.getItem() instanceof ItemFlash;
            case 2:
            case 3:
            case 4:
            case 5: return itemstack.getItem() instanceof ItemRAM;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11: return itemstack.getItem() instanceof ItemPCI;
        }
        return false;
    }
}
