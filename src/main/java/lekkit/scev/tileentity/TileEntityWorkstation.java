package lekkit.scev.tileentity;

import lekkit.scev.items.*;

import net.minecraft.item.ItemStack;

public class TileEntityWorkstation extends TileEntityBaseInventory {
    public TileEntityWorkstation() {
        super(14);
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack itemstack) {
        switch (slotIndex) {
            case 0: return itemstack.getItem() instanceof ItemMotherboard;
            case 1: return itemstack.getItem() instanceof ItemCPU;
            case 2: return itemstack.getItem() instanceof ItemFlash;
            case 3:
            case 4:
            case 5:
            case 6: return itemstack.getItem() instanceof ItemRAM;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12: return itemstack.getItem() instanceof ItemPCI;
        }
        return false;
    }
}
