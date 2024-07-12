package lekkit.scev.inventory;

import lekkit.scev.items.*;

import net.minecraft.item.ItemStack;

public class InventoryMotherboard extends InventoryItem {
    public InventoryMotherboard(ItemStack stack) {
        super(stack, 15);
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
        ItemMotherboard motherboard = (ItemMotherboard)getInventoryItemStack().getItem();
        if (!motherboard.isInventorySlotEnabled(slotIndex)) return false;

        switch (slotIndex) {
            case 0: return itemstack.getItem() instanceof ItemCPU;
            case 1: return itemstack.getItem() instanceof ItemFlash;
            case 2:
            case 3:
            case 4:
            case 5: return itemstack.getItem() instanceof ItemRAM;
            case 6:
            case 7: return itemstack.getItem() instanceof ItemNVMe;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13: return itemstack.getItem() instanceof ItemPCI;
        }
        return false;
    }
}
